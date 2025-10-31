package com.uniflow.academic.period.domain.exception;

public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException(String message) {
        super(message);
    }

    public InvalidPeriodException(String message, Throwable cause) {
        super(message, cause);
    }
}
