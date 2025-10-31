package com.uniflow.academic.period.application.ports.in;

import com.uniflow.academic.period.domain.Period;

/**
 * Query to retrieve the current active period for a student.
 */
public interface GetCurrentPeriodQuery {
    /**
     * Get the currently active period for a student
     *
     * @param studentId The student ID
     * @return The active Period
     * @throws com.uniflow.academic.period.domain.exception.PeriodNotFoundException if no active period found
     */
    Period execute(String studentId);
}
