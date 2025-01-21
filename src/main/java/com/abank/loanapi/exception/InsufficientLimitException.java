package com.abank.loanapi.exception;

public class InsufficientLimitException extends RuntimeException {

    public InsufficientLimitException(String message) {
        super(message);

    }

}
