package com.abank.loanapi.service;

import java.util.List;

import com.abank.loanapi.model.dto.request.CreateLoanRequest;
import com.abank.loanapi.model.dto.response.LoanResponse;

public interface LoanService {

    public LoanResponse createLoan(CreateLoanRequest createLoanRequest);

    public List<LoanResponse> listLoansByCustomer(Long customerId);

}
