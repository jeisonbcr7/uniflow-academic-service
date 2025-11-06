package com.uniflow.academic.subject.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class SubjectHttpResponse {

    @Schema(description = "Subject identifier", example = "subject-ic-6821")
    String id;

    @Schema(description = "Subject name")
    String name;

    @Schema(description = "Subject code")
    String code;

    @Schema(description = "Professor in charge")
    String professor;

    @Schema(description = "Credits amount")
    Integer credits;

    @Schema(description = "Color in HEX")
    String color;

    @Schema(description = "Associated period identifier")
    String periodId;

    @Schema(description = "Subject description")
    String description;

    @Schema(description = "Schedule entries")
    List<String> schedule;

    @Schema(description = "Creation timestamp")
    LocalDateTime createdAt;

    @Schema(description = "Update timestamp")
    LocalDateTime updatedAt;
}
