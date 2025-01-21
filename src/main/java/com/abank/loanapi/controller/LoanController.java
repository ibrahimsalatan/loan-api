package com.abank.loanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abank.loanapi.model.dto.request.CreateLoanRequest;
import com.abank.loanapi.model.dto.request.LoanPaymentRequest;
import com.abank.loanapi.model.dto.response.LoanResponse;
import com.abank.loanapi.model.dto.response.LoanInstallmentResponse;
import com.abank.loanapi.model.dto.response.LoanPaymentResponse;
import com.abank.loanapi.service.impl.LoanServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanServiceImpl loanService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new loan", description = "Create a new loan for a given customer, amount, interest rate and number of installments")
    public LoanResponse createLoan(@Valid @RequestBody CreateLoanRequest request) {
        return loanService.createLoan(request);
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "List loans", description = ": List loans for a given customer")
    public List<LoanResponse> listLoans(@PathVariable Long customerId) {
        return loanService.listLoansByCustomer(customerId);
    }

    @GetMapping("/{loanId}/installments")
    @Operation(summary = "List installements", description = "List installments for a given loan")
    public List<LoanInstallmentResponse> listInstallments(@PathVariable Long loanId) {
        return loanService.listInstallmentsByLoan(loanId);
    }

    @PostMapping("/{loanId}/pay")
    @Operation(summary = "Pay loan", description = "Pay installment for a given loan and amount")
    public LoanPaymentResponse payLoan(@Valid @RequestBody LoanPaymentRequest loanPaymentRequest) {
        return loanService.payLoan(loanPaymentRequest);
    }

}
