package com.abank.loanapi.model.dto.request;

import java.math.BigDecimal;

import com.abank.loanapi.validator.AllowedInstallments;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CreateLoanRequest(
        @NotNull(message = "customer ID can not be empty") @Schema(name = "customer_id", description = "The id of customer") Long customerId,
        @NotNull BigDecimal amount,

        @DecimalMin(value = "0.1", message = "Interest rate must be at least 0.1") @DecimalMax(value = "0.5", message = "Interest rate must be at most 0.5") @Schema(name = "interest_rate") BigDecimal interestRate,
        @AllowedInstallments @Schema(name = "number_of_installments") Integer numberOfInstallments) {

}
