package com.uniflow.academic.period.application.services;

import com.uniflow.academic.period.application.ports.in.UpdatePeriodCommand;
import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.period.application.ports.out.dto.UpdatePeriodRequest;
import com.uniflow.academic.period.domain.exception.PeriodNotFoundException;
import com.uniflow.academic.period.domain.exception.InvalidPeriodException;
import com.uniflow.academic.period.domain.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdatePeriodService implements UpdatePeriodCommand {

    private final PeriodRepository periodRepository;

    @Override
    public Period execute(
            String periodId,
            UpdatePeriodRequest request,
            String studentId
    ) {
        log.info(
                "Updating period: {} for student: {}",
                periodId,
                studentId
        );

        if (!request.hasAnyField()) {
            throw new InvalidPeriodException(
                    "At least one field must be provided for update"
            );
        }

        Period period = periodRepository.findById(periodId, studentId)
                .orElseThrow(() -> new PeriodNotFoundException(
                        "Period not found: " + periodId
                ));

        try {
            // Update only provided fields
            if (request.getName() != null && !request.getName().isBlank()) {
                period = updateFieldReflection(
                        period,
                        "name",
                        request.getName()
                );
            }
            if (request.getType() != null) {
                Period.PeriodType newType =
                        Period.PeriodType.fromValue(request.getType());
                period = updateFieldReflection(
                        period,
                        "type",
                        newType
                );
            }
            if (request.getYear() != null) {
                period = updateFieldReflection(
                        period,
                        "year",
                        request.getYear()
                );
            }
            if (request.getStartDate() != null) {
                period = updateFieldReflection(
                        period,
                        "startDate",
                        request.getStartDate()
                );
            }
            if (request.getEndDate() != null) {
                period = updateFieldReflection(
                        period,
                        "endDate",
                        request.getEndDate()
                );
            }

            // Update timestamp and validate
            period = updateFieldReflection(
                    period,
                    "updatedAt",
                    LocalDateTime.now()
            );
            period.validate();

            Period updatedPeriod = periodRepository.update(period);
            log.info("Period updated successfully: {}", periodId);

            return updatedPeriod;

        } catch (IllegalArgumentException e) {
            log.error("Invalid period type: {}", request.getType(), e);
            throw new InvalidPeriodException(
                    "Invalid period type: " + request.getType()
            );
        } catch (Exception e) {
            log.error("Error updating period: {}", periodId, e);
            throw new InvalidPeriodException(
                    "Failed to update period: " + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Helper method to update immutable Period object fields.
     * Since Period uses Lombok immutables, we need to rebuild it.
     */
    private Period updateFieldReflection(
            Period period,
            String fieldName,
            Object newValue
    ) {
        System.out.println("Updating field: " + fieldName + " to: " + newValue);
        return switch (fieldName) {
            case "name" -> period.toBuilder()
                    .name((String) newValue)
                    .build();
            case "type" -> period.toBuilder()
                    .type((Period.PeriodType) newValue)
                    .build();
            case "year" -> period.toBuilder()
                    .year((Integer) newValue)
                    .build();
            case "startDate" -> period.toBuilder()
                    .startDate((LocalDate) newValue)
                    .build();
            case "endDate" -> period.toBuilder()
                    .endDate((LocalDate) newValue)
                    .build();
            case "updatedAt" -> period.toBuilder()
                    .updatedAt((LocalDateTime) newValue)
                    .build();
            default -> period;
        };
    }
}
