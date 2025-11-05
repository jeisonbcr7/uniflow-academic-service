package com.uniflow.academic.subject.application.ports.in;

import com.uniflow.academic.subject.application.ports.out.dto.SubjectStatisticsResponse;

public interface GetSubjectStatisticsQuery {

    SubjectStatisticsResponse execute(String studentId);
}
