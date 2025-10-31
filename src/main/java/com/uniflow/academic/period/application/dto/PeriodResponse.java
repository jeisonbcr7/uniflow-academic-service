package com.uniflow.academic.period.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PeriodResponse {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}