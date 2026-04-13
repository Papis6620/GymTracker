package com.papis.gymtracker.dto;

import com.papis.gymtracker.model.Exercise;
import com.papis.gymtracker.model.MuscleGroup;
import com.papis.gymtracker.model.enums.*;

import java.util.Set;
import java.util.stream.Collectors;

public record ExerciseResponse(
        String id,
        String name,
        Category category,
        Level level,
        Equipment equipment,
        Force force,
        Mechanic mechanic,
        Set<String> primaryMuscles,
        Set<String> secondaryMuscles
) {
    public static ExerciseResponse from(Exercise exercise){
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getCategory(),
                exercise.getLevel(),
                exercise.getEquipment(),
                exercise.getForce(),
                exercise.getMechanic(),
                exercise.getPrimaryMuscles().stream()
                        .map(MuscleGroup::getName)
                        .collect(Collectors.toSet()),
                exercise.getSecondaryMuscles().stream()
                        .map(MuscleGroup::getName)
                        .collect(Collectors.toSet())
        );
    }
}
