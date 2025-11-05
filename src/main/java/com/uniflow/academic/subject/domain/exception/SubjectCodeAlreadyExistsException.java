package com.uniflow.academic.subject.domain.exception;

public class SubjectCodeAlreadyExistsException extends RuntimeException {
    public SubjectCodeAlreadyExistsException(String message) {
        super(message);
    }
}
