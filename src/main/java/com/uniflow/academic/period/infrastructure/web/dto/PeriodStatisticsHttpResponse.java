package com.uniflow.academic.period.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * HTTP Response DTO for period statistics.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Period statistics response")
public class PeriodStatisticsHttpResponse {

    @JsonProperty("total")
    @Schema(description = "Total number of periods", example = "5")
    private Long total;

    @JsonProperty("active")
    @Schema(description = "Number of active periods", example = "1")
    private Long active;

    @JsonProperty("current")
    @Schema(description = "Number of current periods", example = "1")
    private Long current;

    @JsonProperty("upcoming")
    @Schema(description = "Number of upcoming periods", example = "1")
    private Long upcoming;

    @JsonProperty("finished")
    @Schema(description = "Number of finished periods", example = "3")
    private Long finished;

    @JsonProperty("byType")
    @Schema(description = "Breakdown by period type")
    private Map<String, Long> byType;

    @JsonProperty("averageDuration")
    @Schema(description = "Average duration in days", example = "18.5")
    private Double averageDuration;
}