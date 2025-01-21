package com.abank.loanapi.model.dto.response;

import java.math.BigDecimal;

public record LoanPaymentResponse(

        int installmentsPaid, BigDecimal totalAmountSpent, boolean isLoanPaid

) {

}
