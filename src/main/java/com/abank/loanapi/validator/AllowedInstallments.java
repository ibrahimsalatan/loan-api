package com.abank.loanapi.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedInstallmentsValidator.class)
public @interface AllowedInstallments {
    String message() default "Number of installments must be 6, 9, 12, or 24";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}