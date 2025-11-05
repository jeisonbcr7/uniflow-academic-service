package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.subject.application.ports.in.GetSubjectByIdQuery;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.domain.Subject;
import com.uniflow.academic.subject.domain.exception.SubjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSubjectByIdService implements GetSubjectByIdQuery {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject execute(String subjectId, String studentId) {
        log.info("Fetching subject {} for student {}", subjectId, studentId);
        return subjectRepository.findById(subjectId, studentId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));
    }
}
