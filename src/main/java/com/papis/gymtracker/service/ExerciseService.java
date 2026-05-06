package com.papis.gymtracker.service;

import com.papis.gymtracker.dto.ExerciseResponse;
import com.papis.gymtracker.exception.BadRequestException;
import com.papis.gymtracker.exception.ResourceNotFoundException;
import com.papis.gymtracker.model.enums.Category;
import com.papis.gymtracker.model.enums.Equipment;
import com.papis.gymtracker.model.enums.Level;
import com.papis.gymtracker.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public ExerciseResponse getExerciseById(String id){
        return exerciseRepository.findById(id)
                .map(ExerciseResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
    }

    public List<ExerciseResponse> getExercisesByMuscleGroup(String muscleGroup){
        return exerciseRepository.findByPrimaryMuscles_Name(muscleGroup.toLowerCase())
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public List<ExerciseResponse> getExercisesByLevel(String level){
        try{
            Level l = Level.valueOf(level.toUpperCase());
            return exerciseRepository.findByLevel(l)
                    .stream()
                    .map(ExerciseResponse::from)
                    .toList();
        }catch(IllegalArgumentException e){
            throw new BadRequestException("Invalid level provided: " + level);
        }

    }

    public List<ExerciseResponse> getExercisesByEquipment(String equipment){
        try{
            Equipment e = Equipment.valueOf(equipment.toUpperCase());
            return exerciseRepository.findByEquipment(e)
                    .stream()
                    .map(ExerciseResponse::from)
                    .toList();
        }catch (IllegalArgumentException e){
        throw new BadRequestException("Invalid equipment provided: " + equipment);
        }
    }

    public List<ExerciseResponse> getExercisesByCategory(String category){
        try{
            Category c = Category.valueOf(category.toUpperCase());
            return exerciseRepository.findByCategory(c)
                    .stream()
                    .map(ExerciseResponse::from)
                    .toList();
        }catch (IllegalArgumentException e){
            throw new BadRequestException("Invalid category provided: " + category);
        }

    }
}

