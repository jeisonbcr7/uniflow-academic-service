package com.uniflow.academic.subject.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class SubjectStatisticsHttpResponse {

    @Schema(description = "Total number of subjects")
    Integer total;

    @Schema(description = "Total amount of credits")
    Integer totalCredits;

    @Schema(description = "Average credits per subject")
    Double averageCredits;

    @Schema(description = "Distribution grouped by professor")
    Map<String, Integer> byProfessor;

    @Schema(description = "Distribution grouped by credits")
    Map<Integer, Integer> byCredits;

    @Schema(description = "Distribution grouped by period")
    Map<String, Integer> byPeriod;
}
