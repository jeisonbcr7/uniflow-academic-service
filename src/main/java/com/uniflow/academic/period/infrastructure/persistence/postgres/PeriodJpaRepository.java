package com.uniflow.academic.period.infrastructure.persistence.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodJpaRepository
        extends JpaRepository<PeriodEntity, String> {

    /**
     * Find a period by ID and student ID
     */
    Optional<PeriodEntity> findByIdAndStudentId(
            String id,
            String studentId
    );

    /**
     * Find all periods for a student
     */
    Page<PeriodEntity> findByStudentId(
            String studentId,
            Pageable pageable
    );

    /**
     * Find all periods for a student filtered by type
     */
    Page<PeriodEntity> findByStudentIdAndType(
            String studentId,
            String type,
            Pageable pageable
    );

    /**
     * Find all periods for a student filtered by year
     */
    Page<PeriodEntity> findByStudentIdAndYear(
            String studentId,
            Integer year,
            Pageable pageable
    );

    /**
     * Find all periods for a student filtered by active status
     */
    Page<PeriodEntity> findByStudentIdAndIsActive(
            String studentId,
            Boolean isActive,
            Pageable pageable
    );

    /**
     * Find all periods with multiple filters
     */
    @Query(
            """
            SELECT p FROM PeriodEntity p
            WHERE p.studentId = :studentId
            AND (:type IS NULL OR p.type = :type)
            AND (:year IS NULL OR p.year = :year)
            AND (:isActive IS NULL OR p.isActive = :isActive)
            ORDER BY p.startDate DESC
            """
    )
    Page<PeriodEntity> findAllWithFilters(
            @Param("studentId") String studentId,
            @Param("type") String type,
            @Param("year") Integer year,
            @Param("isActive") Boolean isActive,
            Pageable pageable
    );

    /**
     * Find the current active period for a student
     */
    Optional<PeriodEntity> findByStudentIdAndIsActive(
            String studentId,
            Boolean isActive
    );

    /**
     * Find all active periods for a student
     */
    List<PeriodEntity> findAllByStudentIdAndIsActive(
            String studentId,
            Boolean isActive
    );

    /**
     * Check if period exists for student
     */
    boolean existsByIdAndStudentId(String id, String studentId);

    /**
     * Custom query to find periods overlapping with given dates
     */
    @Query(
            """
            SELECT p FROM PeriodEntity p
            WHERE p.studentId = :studentId
            AND p.startDate <= :endDate
            AND p.endDate >= :startDate
            """
    )
    List<PeriodEntity> findOverlappingPeriods(
            @Param("studentId") String studentId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}