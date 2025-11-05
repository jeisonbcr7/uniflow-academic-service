package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.domain.Subject;

import java.util.List;

public interface GetSubjectsByPeriodQuery {

    List<Subject> execute(String periodId, String studentId);
}
