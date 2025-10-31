package com.uniflow.academic.period.domain.exception;

public class PeriodAlreadyExistsException extends RuntimeException {
    public PeriodAlreadyExistsException(String name) {
        super("Period already exists: " + name);
    }
}
