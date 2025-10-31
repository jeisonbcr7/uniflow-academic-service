package com.uniflow.academic.period.application.services;

import com.uniflow.academic.period.application.ports.in.DeletePeriodCommand;
import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.period.domain.exception.PeriodNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for deleting academic periods.
 * Use case: Delete a period if it has no associated subjects
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeletePeriodService implements DeletePeriodCommand {

    private final PeriodRepository periodRepository;

    @Override
    public void execute(String periodId, String studentId) {
        log.info(
                "Deleting period: {} for student: {}",
                periodId,
                studentId
        );

        // Verify period exists and belongs to student
        if (!periodRepository.existsByIdAndStudentId(periodId, studentId)) {
            throw new PeriodNotFoundException(
                    "Period not found: " + periodId
            );
        }

        // Check if period has associated subjects
        if (periodRepository.hasAssociatedSubjects(periodId)) {
            throw new IllegalStateException(
                    "Cannot delete period with associated subjects"
            );
        }

        periodRepository.delete(periodId, studentId);
        log.info("Period deleted successfully: {}", periodId);
    }
}
