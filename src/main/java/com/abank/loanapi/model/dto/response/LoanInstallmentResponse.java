package com.abank.loanapi.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanInstallmentResponse(
        Long id,
        Long loanId,
        BigDecimal amount,
        BigDecimal paidAmount,
        LocalDate dueDate,
        LocalDate paymentDate,
        Boolean isPaid) {
}