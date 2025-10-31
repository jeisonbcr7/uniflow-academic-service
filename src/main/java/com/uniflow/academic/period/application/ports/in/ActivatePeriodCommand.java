package com.uniflow.academic.period.application.ports.in;

import com.uniflow.academic.period.domain.Period;

/**
 * Command to activate a period (deactivates others for the student).
 */
public interface ActivatePeriodCommand {
    /**
     * Activate a period and deactivate all others for this student
     *
     * @param periodId The period to activate
     * @param studentId The student ID making the request
     * @return The activated Period
     */
    Period execute(String periodId, String studentId);
}
