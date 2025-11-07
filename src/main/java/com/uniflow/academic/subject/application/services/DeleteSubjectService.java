package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.subject.application.ports.in.DeleteSubjectCommand;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.domain.exception.SubjectDeletionException;
import com.uniflow.academic.subject.domain.exception.SubjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeleteSubjectService implements DeleteSubjectCommand {

    private final SubjectRepository subjectRepository;

    @Override
    public void execute(String subjectId, String studentId) {
        log.info("Deleting subject {} for student {}", subjectId, studentId);

        subjectRepository.findById(subjectId, studentId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));

        if (subjectRepository.hasAssociatedTasks(subjectId)) {
            throw new SubjectDeletionException("Cannot delete subject with associated tasks");
        }

        subjectRepository.delete(subjectId, studentId);
        log.info("Subject {} deleted", subjectId);
    }
}
