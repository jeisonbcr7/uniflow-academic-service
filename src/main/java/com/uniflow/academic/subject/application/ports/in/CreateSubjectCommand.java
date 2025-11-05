package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.domain.Subject;

import java.util.List;

public interface CreateSubjectCommand {

    Subject execute(CreateSubjectRequest request, String studentId);

    record CreateSubjectRequest(
            String name,
            String code,
            String professor,
            Integer credits,
            String color,
            String periodId,
            String description,
            List<String> schedule
    ) {
        public CreateSubjectRequest {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Subject name is required");
            }
            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException("Subject code is required");
            }
            if (periodId == null || periodId.isBlank()) {
                throw new IllegalArgumentException("Period is required");
            }
        }
    }
}
