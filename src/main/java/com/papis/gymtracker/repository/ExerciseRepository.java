package com.papis.gymtracker.repository;

import com.papis.gymtracker.model.Exercise;
import com.papis.gymtracker.model.MuscleGroup;
import com.papis.gymtracker.model.enums.Category;
import com.papis.gymtracker.model.enums.Equipment;
import com.papis.gymtracker.model.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository <Exercise, String> {

    List<Exercise> findByPrimaryMuscles_Name(String name);
    List<Exercise> findByLevel(Level level);
    List<Exercise> findByEquipment(Equipment equipment);
    List<Exercise> findByCategory(Category category);
}
