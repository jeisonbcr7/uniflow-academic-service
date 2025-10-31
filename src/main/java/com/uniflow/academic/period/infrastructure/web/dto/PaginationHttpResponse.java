package com.uniflow.academic.period.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * HTTP Response DTO for paginated periods.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Paginated periods response")
public class PaginationHttpResponse {

    @JsonProperty("data")
    @Schema(description = "List of periods")
    private List<PeriodHttpResponse> data;

    @JsonProperty("pagination")
    @Schema(description = "Pagination metadata")
    private Pagination pagination;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Pagination metadata")
    public static class Pagination {

        @JsonProperty("page")
        @Schema(description = "Current page number", example = "1")
        private Integer page;

        @JsonProperty("limit")
        @Schema(description = "Items per page", example = "10")
        private Integer limit;

        @JsonProperty("total")
        @Schema(description = "Total number of items", example = "100")
        private Long total;

        @JsonProperty("totalPages")
        @Schema(description = "Total number of pages", example = "10")
        private Integer totalPages;

        @JsonProperty("hasNext")
        @Schema(description = "Whether there is a next page")
        private Boolean hasNext;

        @JsonProperty("hasPrevious")
        @Schema(description = "Whether there is a previous page")
        private Boolean hasPrevious;
    }
}