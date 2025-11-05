package com.uniflow.academic.subject.domain.exception;

public class InvalidSubjectException extends RuntimeException {
    public InvalidSubjectException(String message) {
        super(message);
    }

    public InvalidSubjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
