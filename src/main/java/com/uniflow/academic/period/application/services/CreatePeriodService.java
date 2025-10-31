package com.uniflow.academic.period.application.services;

import com.uniflow.academic.period.application.ports.in.CreatePeriodCommand;
import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.period.domain.exception.InvalidPeriodException;
import com.uniflow.academic.period.domain.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreatePeriodService implements CreatePeriodCommand {

    private final PeriodRepository periodRepository;

    @Override
    public Period execute(CreatePeriodRequest request, String studentId) {
        log.info(
                "Creating new period: {} for student: {}",
                request.name(),
                studentId
        );

        try {
            // Convert request type string to enum
            Period.PeriodType periodType =
                    Period.PeriodType.fromValue(request.type());

            // Create domain object - this validates business rules
            Period period = Period.create(
                    request.name(),
                    periodType,
                    request.year(),
                    request.startDate(),
                    request.endDate(),
                    studentId
            );

            // Persist period
            Period savedPeriod = periodRepository.save(period);
            log.info("Period created successfully with id: {}", savedPeriod.getId());

            return savedPeriod;

        } catch (IllegalArgumentException e) {
            log.error("Invalid period type: {}", request.type(), e);
            throw new InvalidPeriodException(
                    "Invalid period type: " + request.type()
            );
        } catch (Exception e) {
            log.error("Error creating period for student: {}", studentId, e);
            throw new InvalidPeriodException(
                    "Failed to create period: " + e.getMessage(),
                    e
            );
        }
    }
}
