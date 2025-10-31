package com.uniflow.academic.period.application.ports.out;

import com.uniflow.academic.period.domain.Period;
import com.uniflow.academic.period.application.ports.out.dto.PeriodFilter;
import com.uniflow.academic.period.application.ports.out.dto.PaginationParams;
import com.uniflow.academic.period.application.ports.in.GetAllPeriodsQuery;
import java.util.List;
import java.util.Optional;

public interface PeriodRepository {
    /**
     * Save a new period to persistence
     *
     * @param period The period to save
     * @return The saved period with ID
     */
    Period save(Period period);

    /**
     * Update an existing period
     *
     * @param period The period to update
     * @return The updated period
     */
    Period update(Period period);

    /**
     * Find a period by ID and student ID
     *
     * @param periodId The period ID
     * @param studentId The student ID (for data isolation)
     * @return Optional containing the period if found
     */
    Optional<Period> findById(String periodId, String studentId);

    /**
     * Find all periods for a student with filters and pagination
     *
     * @param studentId The student ID
     * @param params Pagination parameters
     * @param filter Optional filters
     * @return Paginated response
     */
    GetAllPeriodsQuery.PaginatedPeriodsResponse findAll(
            String studentId,
            PaginationParams params,
            PeriodFilter filter
    );

    /**
     * Find the currently active period for a student
     *
     * @param studentId The student ID
     * @return Optional containing the active period if found
     */
    Optional<Period> findCurrentActive(String studentId);

    /**
     * Find all active periods for a student
     *
     * @param studentId The student ID
     * @return List of active periods
     */
    List<Period> findAllActive(String studentId);

    /**
     * Delete a period
     *
     * @param periodId The period ID
     * @param studentId The student ID (for authorization)
     */
    void delete(String periodId, String studentId);

    /**
     * Check if period has associated subjects
     *
     * @param periodId The period ID
     * @return true if period has subjects
     */
    boolean hasAssociatedSubjects(String periodId);

    /**
     * Check if period exists for student
     *
     * @param periodId The period ID
     * @param studentId The student ID
     * @return true if period exists
     */
    boolean existsByIdAndStudentId(String periodId, String studentId);
}
