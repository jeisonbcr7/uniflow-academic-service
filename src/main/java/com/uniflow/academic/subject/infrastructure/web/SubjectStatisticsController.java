package com.uniflow.academic.subject.infrastructure.web;

import com.uniflow.academic.subject.application.ports.in.GetSubjectStatisticsQuery;
import com.uniflow.academic.subject.infrastructure.web.dto.SubjectStatisticsHttpResponse;
import com.uniflow.academic.subject.infrastructure.web.dto.mapper.SubjectHttpMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Tag(name = "Subjects", description = "Subject management endpoints")
@SecurityRequirement(name = "bearer-jwt")
public class SubjectStatisticsController {

    private final GetSubjectStatisticsQuery getSubjectStatisticsQuery;
    private final SubjectHttpMapper mapper;

    @GetMapping("/subjects")
    @Operation(summary = "Get subject statistics")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SubjectStatisticsHttpResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<SubjectStatisticsHttpResponse> getSubjectStatistics(Authentication authentication) {
        log.info("GET /stats/subjects - subject statistics");
        String studentId = authentication.getName();
        var response = getSubjectStatisticsQuery.execute(studentId);
        return ResponseEntity.ok(mapper.toStatisticsHttpResponse(response));
    }
}
