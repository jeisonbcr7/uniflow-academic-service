package com.uniflow.academic.period.infrastructure.persistence.postgres;

import com.uniflow.academic.period.application.ports.in.GetAllPeriodsQuery;
import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.period.application.ports.out.dto.PeriodFilter;
import com.uniflow.academic.period.application.ports.out.dto.PaginationParams;
import com.uniflow.academic.period.domain.Period;
import com.uniflow.academic.subject.infrastructure.persistence.postgres.SubjectJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PeriodPostgresAdapter implements PeriodRepository {

    private final PeriodJpaRepository jpaRepository;
    private final PeriodEntityMapper mapper;
    private final SubjectJpaRepository subjectJpaRepository;

    @Override
    public Period save(Period period) {
        log.debug("Saving period: {}", period.getId());
        PeriodEntity entity = mapper.toEntity(period);
        PeriodEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Period update(Period period) {
        log.debug("Updating period: {}", period.getId());
        PeriodEntity entity = mapper.toEntity(period);
        PeriodEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Optional<Period> findById(String periodId, String studentId) {
        log.debug(
                "Finding period: {} for student: {}",
                periodId,
                studentId
        );
        return jpaRepository.findByIdAndStudentId(periodId, studentId)
                .map(mapper::toDomain);
    }

    @Override
    public GetAllPeriodsQuery.PaginatedPeriodsResponse findAll(
            String studentId,
            PaginationParams params,
            PeriodFilter filter
    ) {
        log.debug(
                "Finding all periods for student: {} with filters: {}",
                studentId,
                filter
        );

        params.validate();
        Pageable pageable = PageRequest.of(
                params.getPage() - 1,
                params.getLimit()
        );

        Page<PeriodEntity> page;

        // Build query based on filters
        if (filter != null && !filter.isEmpty()) {
            page = jpaRepository.findAllWithFilters(
                    studentId,
                    filter.getType(),
                    filter.getYear(),
                    filter.getIsActive(),
                    pageable
            );
        } else {
            page = jpaRepository.findByStudentId(studentId, pageable);
        }

        List<Period> periods = page.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        GetAllPeriodsQuery.PaginatedPeriodsResponse.Pagination pagination =
                GetAllPeriodsQuery.PaginatedPeriodsResponse.Pagination.builder()
                        .page(params.getPage())
                        .limit(params.getLimit())
                        .total(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .hasNext(page.hasNext())
                        .hasPrevious(page.hasPrevious())
                        .build();

        return GetAllPeriodsQuery.PaginatedPeriodsResponse.builder()
                .data(periods)
                .pagination(pagination)
                .build();
    }

    @Override
    public Optional<Period> findCurrentActive(String studentId) {
        log.debug("Finding current active period for student: {}", studentId);
        return jpaRepository.findByStudentIdAndIsActive(studentId, true)
                .map(mapper::toDomain);
    }

    @Override
    public List<Period> findAllActive(String studentId) {
        log.debug("Finding all active periods for student: {}", studentId);
        return jpaRepository.findAllByStudentIdAndIsActive(studentId, true)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String periodId, String studentId) {
        log.debug(
                "Deleting period: {} for student: {}",
                periodId,
                studentId
        );
        jpaRepository.deleteById(periodId);
    }

    @Override
    public boolean hasAssociatedSubjects(String periodId) {
        log.debug("Checking subjects for period: {}", periodId);
        return subjectJpaRepository.countByPeriodId(periodId) > 0;
    }

    @Override
    public boolean existsByIdAndStudentId(String periodId, String studentId) {
        log.debug(
                "Checking existence of period: {} for student: {}",
                periodId,
                studentId
        );
        return jpaRepository.existsByIdAndStudentId(periodId, studentId);
    }
}