package com.uniflow.academic.period.infrastructure.web.dto.mapper;

import com.uniflow.academic.period.application.ports.in.CreatePeriodCommand;
import com.uniflow.academic.period.application.ports.in.GetAllPeriodsQuery;
import com.uniflow.academic.period.application.ports.out.dto.PeriodStatisticsResponse;
import com.uniflow.academic.period.application.ports.out.dto.UpdatePeriodRequest;
import com.uniflow.academic.period.domain.Period;
import com.uniflow.academic.period.infrastructure.web.dto.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper between HTTP DTOs and application layer objects.
 * Converts between HTTP requests/responses and domain/application models.
 */
@Component
public class PeriodHttpMapper {

    /**
     * Convert HTTP create request to application command request
     */
    public CreatePeriodCommand.CreatePeriodRequest
    toCreatePeriodCommandRequest(CreatePeriodHttpRequest httpRequest) {

        if (httpRequest == null) {
            return null;
        }

        return new CreatePeriodCommand.CreatePeriodRequest(
                httpRequest.getName(),
                httpRequest.getType(),
                httpRequest.getYear(),
                httpRequest.getStartDate(),
                httpRequest.getEndDate()
        );
    }

    /**
     * Convert HTTP update request to application command request
     */
    public UpdatePeriodRequest toUpdatePeriodCommandRequest(UpdatePeriodHttpRequest httpRequest) {

        if (httpRequest == null) {
            return null;
        }

        return new UpdatePeriodRequest(
                httpRequest.getName(),
                httpRequest.getType(),
                httpRequest.getYear(),
                httpRequest.getStartDate(),
                httpRequest.getEndDate()
        );
    }

    /**
     * Convert domain Period to HTTP response DTO
     */
    public PeriodHttpResponse toHttpResponse(Period period) {
        if (period == null) {
            return null;
        }

        return PeriodHttpResponse.builder()
                .id(period.getId())
                .name(period.getName())
                .type(period.getType().getValue())
                .year(period.getYear())
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .studentId(period.getStudentId())
                .isActive(period.getIsActive())
                .createdAt(period.getCreatedAt())
                .updatedAt(period.getUpdatedAt())
                .build();
    }

    /**
     * Convert paginated periods to HTTP response
     */
    public PaginationHttpResponse toPaginationHttpResponse(
            GetAllPeriodsQuery.PaginatedPeriodsResponse response
    ) {
        if (response == null) {
            return null;
        }

        List<PeriodHttpResponse> periodResponses = response.getData().stream()
                .map(this::toHttpResponse)
                .collect(Collectors.toList());

        PaginationHttpResponse.Pagination pagination =
                PaginationHttpResponse.Pagination.builder()
                        .page(response.getPagination().getPage())
                        .limit(response.getPagination().getLimit())
                        .total(response.getPagination().getTotal())
                        .totalPages(response.getPagination().getTotalPages())
                        .hasNext(response.getPagination().getHasNext())
                        .hasPrevious(response.getPagination().getHasPrevious())
                        .build();

        return PaginationHttpResponse.builder()
                .data(periodResponses)
                .pagination(pagination)
                .build();
    }

    /**
     * Convert statistics to HTTP response
     */
    public PeriodStatisticsHttpResponse toStatisticsHttpResponse(
            PeriodStatisticsResponse response
    ) {
        if (response == null) {
            return null;
        }

        return PeriodStatisticsHttpResponse.builder()
                .total(response.getTotal())
                .active(response.getActive())
                .current(response.getCurrent())
                .upcoming(response.getUpcoming())
                .finished(response.getFinished())
                .byType(response.getByType())
                .averageDuration(response.getAverageDuration())
                .build();
    }
}