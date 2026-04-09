package com.papis.gymtracker.repository;

import com.papis.gymtracker.model.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
    List<WorkoutEntry> findBySessionUserId(Long workoutSessionId);
    List<WorkoutEntry> findByExerciseIdAndSessionUserId(String exerciseId, Long userId);

}
