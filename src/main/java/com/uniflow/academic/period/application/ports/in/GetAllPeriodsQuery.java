package com.uniflow.academic.period.application.ports.in;

import com.uniflow.academic.period.application.ports.out.dto.PaginationParams;
import com.uniflow.academic.period.application.ports.out.dto.PeriodFilter;
import com.uniflow.academic.period.domain.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public interface GetAllPeriodsQuery {
    /**
     * Get all periods for a student with optional filters and pagination
     *
     * @param studentId The student ID
     * @param params Pagination parameters (page, limit)
     * @param filter Optional filters (type, year, isActive)
     * @return Paginated periods response
     */
    PaginatedPeriodsResponse execute(
            String studentId,
            PaginationParams params,
            PeriodFilter filter
    );

    /**
     * DTO for paginated periods response
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class PaginatedPeriodsResponse {
        private List<Period> data;
        private Pagination pagination;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Pagination {
            private Integer page;
            private Integer limit;
            private Long total;
            private Integer totalPages;
            private Boolean hasNext;
            private Boolean hasPrevious;
        }
    }
}