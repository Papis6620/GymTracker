package com.papis.gymtracker.dto;

import com.papis.gymtracker.model.WorkoutEntry;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgressResponse(
        LocalDate sessionDate,
        Integer sets,
        Integer reps,
        BigDecimal weightKg
) {
    public static ProgressResponse from(WorkoutEntry entry){
        return new ProgressResponse(
                entry.getSession().getSessionDate(),
                entry.getSets(),
                entry.getReps(),
                entry.getWeightKg()
        );
    }
}
