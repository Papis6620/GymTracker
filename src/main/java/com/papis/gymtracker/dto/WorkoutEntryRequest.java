package com.papis.gymtracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WorkoutEntryRequest(
        @NotBlank String exerciseId,
        @NotNull @Min(1) Integer sets,
        @NotNull @Min(1) Integer reps,
        BigDecimal weightKg
) {}
