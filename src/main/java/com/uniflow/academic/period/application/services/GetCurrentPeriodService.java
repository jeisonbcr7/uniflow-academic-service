package com.uniflow.academic.period.application.services;

import com.uniflow.academic.period.application.ports.in.GetCurrentPeriodQuery;
import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.period.domain.exception.PeriodNotFoundException;
import com.uniflow.academic.period.domain.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for retrieving the current active period.
 * Use case: Get the currently active period for a student
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCurrentPeriodService implements GetCurrentPeriodQuery {

    private final PeriodRepository periodRepository;

    @Override
    public Period execute(String studentId) {
        log.info("Fetching current active period for student: {}", studentId);

        Period currentPeriod = periodRepository.findCurrentActive(studentId)
                .orElseThrow(() -> {
                    log.warn(
                            "No active period found for student: {}",
                            studentId
                    );
                    return new PeriodNotFoundException(
                            "No active period found for student"
                    );
                });

        log.debug("Current period found: {}", currentPeriod.getId());
        return currentPeriod;
    }
}