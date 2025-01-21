package com.abank.loanapi.model.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record LoanPaymentRequest(
        @NotNull(message = "Loan ID can not be empty") @Schema(name = "loan_id", description = "The id of loan") Long loanId,
        @NotNull BigDecimal amount

) {

}
