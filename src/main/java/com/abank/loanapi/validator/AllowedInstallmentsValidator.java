package com.abank.loanapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class AllowedInstallmentsValidator implements ConstraintValidator<AllowedInstallments, Integer> {

    private static final List<Integer> ALLOWED_VALUES = Arrays.asList(6, 9, 12, 24);

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Null values are handled by @NotNull
        }
        return ALLOWED_VALUES.contains(value);
    }
}