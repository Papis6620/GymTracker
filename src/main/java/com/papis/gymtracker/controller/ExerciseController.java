package com.papis.gymtracker.controller;

import com.papis.gymtracker.dto.ExerciseResponse;
import com.papis.gymtracker.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseResponse> getAllExercises(
            @RequestParam(required = false) String muscle,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String equipment

    ) {
        if (muscle != null) return exerciseService.getExercisesByMuscleGroup(muscle);
        if (level != null) return exerciseService.getExercisesByLevel(level);
        if (category != null) return exerciseService.getExercisesByCategory(category);
        if (equipment != null) return exerciseService.getExercisesByEquipment(equipment);
        return exerciseService.getAllExercises();
    }

    @GetMapping("/{id}")
    public ExerciseResponse getExerciseById(@PathVariable String id){
        return exerciseService.getExerciseById(id);
    }

}