package com.uniflow.academic.period.application.ports.in;

public interface DeletePeriodCommand {
    /**
     * Delete a period if it has no associated subjects
     *
     * @param periodId The period to delete
     * @param studentId The student ID making the request
     * @throws IllegalStateException if period has associated subjects
     */
    void execute(String periodId, String studentId);
}
