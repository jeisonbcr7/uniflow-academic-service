package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.domain.Subject;
import com.uniflow.academic.subject.application.ports.out.dto.SubjectFilter;

import java.util.List;

public interface GetSubjectsQuery {

    List<Subject> execute(String studentId, SubjectFilter filter);
}
