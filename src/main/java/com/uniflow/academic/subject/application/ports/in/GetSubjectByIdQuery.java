package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.domain.Subject;

public interface GetSubjectByIdQuery {

    Subject execute(String subjectId, String studentId);
}
