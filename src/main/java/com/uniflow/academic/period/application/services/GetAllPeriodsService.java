package com.uniflow.academic.period.application.services;

import com.uniflow.academic.period.application.ports.in.GetAllPeriodsQuery;
import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.period.application.ports.out.dto.PeriodFilter;
import com.uniflow.academic.period.application.ports.out.dto.PaginationParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for retrieving all periods.
 * Use case: Get all periods for a student with optional filters and pagination
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllPeriodsService implements GetAllPeriodsQuery {

    private final PeriodRepository periodRepository;

    @Override
    public PaginatedPeriodsResponse execute(
            String studentId,
            PaginationParams params,
            PeriodFilter filter
    ) {
        log.info(
                "Fetching periods for student: {} with page: {}, limit: {}",
                studentId,
                params.getPage(),
                params.getLimit()
        );

        // Validate and normalize pagination params
        params.validate();

        PaginatedPeriodsResponse response = periodRepository.findAll(
                studentId,
                params,
                filter
        );

        log.info(
                "Retrieved {} periods out of {} total",
                response.getData().size(),
                response.getPagination().getTotal()
        );

        return response;
    }
}