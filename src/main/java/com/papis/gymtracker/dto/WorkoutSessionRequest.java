package com.papis.gymtracker.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record WorkoutSessionRequest(
        @NotNull LocalDate sessionDate,
        String notes
){}
