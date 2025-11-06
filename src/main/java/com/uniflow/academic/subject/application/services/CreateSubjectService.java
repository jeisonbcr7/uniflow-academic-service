package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.subject.application.ports.in.CreateSubjectCommand;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.domain.Subject;
import com.uniflow.academic.subject.domain.exception.InvalidSubjectException;
import com.uniflow.academic.subject.domain.exception.SubjectCodeAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateSubjectService implements CreateSubjectCommand {

    private final SubjectRepository subjectRepository;
    private final PeriodRepository periodRepository;

    @Override
    public Subject execute(CreateSubjectRequest request, String studentId) {
        log.info("Creating subject {} for student {}", request.name(), studentId);

        if (!periodRepository.existsByIdAndStudentId(request.periodId(), studentId)) {
            throw new InvalidSubjectException("Period does not belong to the authenticated student");
        }

        if (subjectRepository.existsByCode(request.code(), request.periodId(), studentId, null)) {
            throw new SubjectCodeAlreadyExistsException(
                    "Subject code already exists in this period"
            );
        }

        List<String> schedule = request.schedule() != null
                ? request.schedule()
                : List.of();

        Subject subject = Subject.create(
                request.name(),
                request.code(),
                request.professor(),
                request.credits(),
                request.color(),
                request.periodId(),
                studentId,
                request.description(),
                schedule
        );

        Subject saved = subjectRepository.save(subject);
        log.info("Subject {} created successfully", saved.getId());
        return saved;
    }
}
