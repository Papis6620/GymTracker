package com.papis.gymtracker.dto;

import java.math.BigDecimal;

public record WorkoutEntryResponse(
        Long id,
        String exerciseId,
        String exerciseName,
        Integer sets,
        Integer reps,
        BigDecimal weightKg
) {
    public static WorkoutEntryResponse from(com.papis.gymtracker.model.WorkoutEntry entry){
        return new WorkoutEntryResponse(
                entry.getId(),
                entry.getExercise().getId(),
                entry.getExercise().getName(),
                entry.getSets(),
                entry.getReps(),
                entry.getWeightKg()
        );
    }
}
