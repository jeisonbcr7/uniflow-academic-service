package com.uniflow.academic.subject.application.ports.out.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class SubjectStatisticsResponse {
    Integer total;
    Integer totalCredits;
    Double averageCredits;
    Map<String, Integer> byProfessor;
    Map<Integer, Integer> byCredits;
    Map<String, Integer> byPeriod;
}
