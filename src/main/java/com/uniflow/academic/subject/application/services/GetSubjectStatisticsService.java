package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.subject.application.ports.in.GetSubjectStatisticsQuery;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.application.ports.out.dto.SubjectStatisticsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSubjectStatisticsService implements GetSubjectStatisticsQuery {

    private final SubjectRepository subjectRepository;

    @Override
    public SubjectStatisticsResponse execute(String studentId) {
        log.info("Fetching subject statistics for student {}", studentId);
        return subjectRepository.getStatistics(studentId);
    }
}
