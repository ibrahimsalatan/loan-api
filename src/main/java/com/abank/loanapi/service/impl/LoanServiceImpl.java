package com.abank.loanapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abank.loanapi.exception.ResourceNotFoundException;
import com.abank.loanapi.exception.InsufficientLimitException;
import com.abank.loanapi.model.dto.request.CreateLoanRequest;
import com.abank.loanapi.model.dto.request.LoanPaymentRequest;
import com.abank.loanapi.model.dto.response.LoanResponse;
import com.abank.loanapi.model.dto.response.LoanInstallmentResponse;
import com.abank.loanapi.model.dto.response.LoanPaymentResponse;
import com.abank.loanapi.model.entity.Customer;
import com.abank.loanapi.model.entity.Loan;
import com.abank.loanapi.model.entity.LoanInstallment;
import com.abank.loanapi.model.mapper.LoanInstallmentMapper;
import com.abank.loanapi.model.mapper.LoanMapper;
import com.abank.loanapi.repository.LoanRepository;
import com.abank.loanapi.service.CustomerService;
import com.abank.loanapi.service.LoanService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

        private static final BigDecimal ADJUSTMENT_MULTIPLIER = BigDecimal.valueOf(0.001);

        private final CustomerService customerService;
        private final LoanRepository loanRepository;
        private final LoanMapper loanMapper;
        private final LoanInstallmentMapper loanInstallmentMapper;

        @Transactional()
        public LoanResponse createLoan(CreateLoanRequest createLoanRequest) {

                Customer customer = customerService.findCustomerById(createLoanRequest.customerId());

                if (customer.getUsedCreditLimit().add(createLoanRequest.amount())
                                .compareTo(customer.getCreditLimit()) > 0) {
                        throw new InsufficientLimitException("Customer does not have enough credit limit.");
                }

                BigDecimal totalAmount = createLoanRequest.amount()
                                .multiply(BigDecimal.ONE.add(createLoanRequest.interestRate()));
                BigDecimal installmentAmount = totalAmount.divide(
                                BigDecimal.valueOf(createLoanRequest.numberOfInstallments()),
                                2, RoundingMode.HALF_UP);

                LocalDate createDate = LocalDate.now();
                Loan loan = Loan.builder().customer(customer).loanAmount(createLoanRequest.amount())
                                .numberOfInstallments(createLoanRequest.numberOfInstallments())
                                .createDate(createDate).isPaid(false).build();

                List<LoanInstallment> loanInstallments = new ArrayList<>();
                for (int i = 0; i < createLoanRequest.numberOfInstallments(); i++) {
                        LocalDate dueDate = createDate.plusMonths(i + 1).withDayOfMonth(1);
                        LoanInstallment installment = LoanInstallment.builder().loan(loan)
                                        .amount(installmentAmount)
                                        .dueDate(dueDate).build();
                        loanInstallments.add(installment);

                }
                loan.setInstallments(loanInstallments);
                customer.setUsedCreditLimit(customer.getUsedCreditLimit().add(createLoanRequest.amount()));
                loanRepository.save(loan);

                return loanMapper.toDTO(loan);
        }

        public List<LoanResponse> listLoansByCustomer(Long customerId) {
                Customer customer = customerService.findCustomerById(customerId);
                return customer.getLoans().stream().map(loanMapper::toDTO)
                                .toList();
        }

        public Loan updateLoan(Loan loan) {
                return loanRepository.save(loan);
        }

        public List<LoanInstallmentResponse> listInstallmentsByLoan(Long loanId) {
                Loan loan = findLoanById(loanId);
                return loan.getInstallments().stream().map(loanInstallmentMapper::toDTO).toList();
        }

        @Transactional
        public LoanPaymentResponse payLoan(LoanPaymentRequest paymentRequest) {
                Loan loan = findLoanById(paymentRequest.loanId());

                LocalDate currentDate = LocalDate.now();
                List<LoanInstallment> allInstallments = loan.getInstallments();
                List<LoanInstallment> unpaidWithinAllowedDueDate = allInstallments.stream()
                                .filter(installment -> !installment.getIsPaid()
                                                && installment.getDueDate().isBefore(currentDate.plusMonths(2)))
                                .toList();

                int installmentsPaid = 0;
                BigDecimal totalAmountSpent = BigDecimal.ZERO;
                BigDecimal remainingAmount = paymentRequest.amount();

                for (LoanInstallment installment : unpaidWithinAllowedDueDate) {

                        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(
                                        installment.getDueDate(), currentDate);

                        BigDecimal adjustmentFactor = installment.getAmount()
                                        .multiply(ADJUSTMENT_MULTIPLIER)
                                        .multiply(BigDecimal.valueOf(daysDifference));

                        BigDecimal adjustedAmount = installment.getAmount();
                        adjustedAmount = adjustedAmount.add(adjustmentFactor);
                       
                        boolean canPayInstallment = remainingAmount.compareTo(adjustedAmount) >= 0;

                        if (canPayInstallment) {
                                installment.setPaidAmount(adjustedAmount);
                                installment.setPaymentDate(currentDate);
                                installment.setIsPaid(true);

                                remainingAmount = remainingAmount.subtract(adjustedAmount);
                                totalAmountSpent = totalAmountSpent.add(adjustedAmount);
                                installmentsPaid++;
                        }
                }

                Customer customer = loan.getCustomer();
                customer.setUsedCreditLimit(customer.getUsedCreditLimit().subtract(totalAmountSpent));

                boolean isLoanPaid = allInstallments.stream().allMatch(LoanInstallment::getIsPaid);
                loan.setIsPaid(isLoanPaid);
                loanRepository.save(loan);

                return new LoanPaymentResponse(installmentsPaid, totalAmountSpent, isLoanPaid);

        }

        private Loan findLoanById(Long loanId) {
                return loanRepository.findById(loanId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Loan not found with the given ID:" + loanId));
        }

}
