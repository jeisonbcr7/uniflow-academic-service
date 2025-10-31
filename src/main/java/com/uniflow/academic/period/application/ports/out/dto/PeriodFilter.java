package com.uniflow.academic.period.application.ports.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for period filtering criteria.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodFilter {
    private String type;           // first-semester, second-semester, summer, special
    private Integer year;
    private Boolean isActive;

    public boolean isEmpty() {
        return type == null && year == null && isActive == null;
    }
}
