package com.uniflow.academic.subject.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidateSubjectCodeHttpRequest {

    @Schema(description = "Subject code to validate", example = "IC-5401")
    @NotBlank
    String code;

    @Schema(description = "Associated period identifier", example = "period-2025-1")
    @NotBlank
    String periodId;

    @Schema(description = "Current subject identifier when updating")
    String subjectId;
}
