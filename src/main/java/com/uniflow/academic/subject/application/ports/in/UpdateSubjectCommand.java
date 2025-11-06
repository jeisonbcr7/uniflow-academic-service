package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.domain.Subject;

import java.util.List;

public interface UpdateSubjectCommand {

    Subject execute(String subjectId, UpdateSubjectRequest request, String studentId);

    record UpdateSubjectRequest(
            String name,
            String code,
            String professor,
            Integer credits,
            String color,
            String description,
            List<String> schedule
    ) {
        public UpdateSubjectRequest {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Subject name is required");
            }
            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException("Subject code is required");
            }
        }
    }
}
