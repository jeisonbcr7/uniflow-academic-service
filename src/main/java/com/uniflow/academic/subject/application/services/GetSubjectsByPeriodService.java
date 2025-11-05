package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.subject.application.ports.in.GetSubjectsByPeriodQuery;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.domain.Subject;
import com.uniflow.academic.subject.domain.exception.InvalidSubjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSubjectsByPeriodService implements GetSubjectsByPeriodQuery {

    private final SubjectRepository subjectRepository;
    private final PeriodRepository periodRepository;

    @Override
    public List<Subject> execute(String periodId, String studentId) {
        log.info("Fetching subjects for period {} and student {}", periodId, studentId);

        if (!periodRepository.existsByIdAndStudentId(periodId, studentId)) {
            throw new InvalidSubjectException("Period does not belong to the authenticated student");
        }

        return subjectRepository.findByPeriodId(periodId, studentId);
    }
}
