package com.uniflow.academic.period.application.ports.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * DTO for period statistics response.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodStatisticsResponse {
    private Long total;
    private Long active;
    private Long current;
    private Long upcoming;
    private Long finished;

    @JsonProperty("byType")
    private Map<String, Long> byType;

    @JsonProperty("averageDuration")
    private Double averageDuration;
}
