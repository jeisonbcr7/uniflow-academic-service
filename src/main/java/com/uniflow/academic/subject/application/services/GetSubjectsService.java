package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.subject.application.ports.in.GetSubjectsQuery;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.application.ports.out.dto.SubjectFilter;
import com.uniflow.academic.subject.domain.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSubjectsService implements GetSubjectsQuery {

    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> execute(String studentId, SubjectFilter filter) {
        log.info("Fetching subjects for student {}", studentId);
        return subjectRepository.findAll(studentId, filter);
    }
}
