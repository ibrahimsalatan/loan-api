package com.abank.loanapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abank.loanapi.exception.InsufficientLimitException;
import com.abank.loanapi.model.dto.request.CreateLoanRequest;
import com.abank.loanapi.model.entity.Customer;
import com.abank.loanapi.model.entity.Loan;
import com.abank.loanapi.model.mapper.LoanMapper;
import com.abank.loanapi.repository.LoanRepository;
import com.abank.loanapi.service.impl.LoanServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanServiceImpl loanService;

    private CreateLoanRequest loanRequest;

    @BeforeEach
    void init() {
        loanRequest = new CreateLoanRequest(1L, BigDecimal.valueOf(80),
                BigDecimal.valueOf(0.2),
                Integer.valueOf(4));
    }

    @Test
    void shouldCreateLoanWhenCreditIsSufficient() {

        Customer customer = Customer.builder().id(1L).name("customer1")
                .surname("cust_surname")
                .creditLimit(BigDecimal.valueOf(2000)).usedCreditLimit(BigDecimal.valueOf(200)).build();

        when(customerService.findCustomerById(customer.getId())).thenReturn(customer);
        when(loanRepository.save(org.mockito.ArgumentMatchers.any(Loan.class))).thenReturn(new Loan());

        assertDoesNotThrow(() -> loanService.createLoan(loanRequest));
    }

    @Test
    void shouldThrowInsufficientCreditException() {

        Customer customer = Customer.builder().id(1L).name("customer1")
                .surname("cust_surname")
                .creditLimit(BigDecimal.valueOf(100)).usedCreditLimit(BigDecimal.valueOf(90)).build();

        when(customerService.findCustomerById(customer.getId())).thenReturn(customer);

        assertThrows(InsufficientLimitException.class, () -> loanService.createLoan(loanRequest));
    }

}
