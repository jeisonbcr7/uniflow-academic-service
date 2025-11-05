package com.uniflow.academic.subject.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SubjectsByPeriodHttpResponse {

    @Schema(description = "Subjects within the period")
    List<SubjectHttpResponse> subjects;
}
