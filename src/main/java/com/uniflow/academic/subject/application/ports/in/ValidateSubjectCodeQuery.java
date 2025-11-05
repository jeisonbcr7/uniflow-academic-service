package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.application.ports.out.dto.ValidateSubjectCodeResponse;

public interface ValidateSubjectCodeQuery {

    ValidateSubjectCodeResponse execute(
            String code,
            String periodId,
            String studentId,
            String subjectId
    );
}
