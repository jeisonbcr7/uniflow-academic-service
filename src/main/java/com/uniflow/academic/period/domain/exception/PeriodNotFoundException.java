package com.uniflow.academic.period.domain.exception;

public class PeriodNotFoundException extends RuntimeException {
    public PeriodNotFoundException(Long id) {
        super("Period with id " + id + " not found");
    }
    public PeriodNotFoundException(String message) {
        super(message);
    }
}
