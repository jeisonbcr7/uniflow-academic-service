package com.uniflow.academic.subject.application.ports.out.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidateSubjectCodeResponse {
    boolean isValid;
    boolean isAvailable;
    String message;
}
