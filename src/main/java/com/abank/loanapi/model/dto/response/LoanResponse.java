package com.abank.loanapi.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long customerId,
        BigDecimal loanAmount,
        Integer numberOfInstallments,
        LocalDate createDate,
        Boolean isPaid) {
}