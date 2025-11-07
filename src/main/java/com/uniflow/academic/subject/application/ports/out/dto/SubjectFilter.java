package com.uniflow.academic.subject.application.ports.out.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubjectFilter {
    String periodId;
    String professor;
    Integer credits;
    String search;

    public boolean isEmpty() {
        return (periodId == null || periodId.isBlank())
                && (professor == null || professor.isBlank())
                && credits == null
                && (search == null || search.isBlank());
    }
}
