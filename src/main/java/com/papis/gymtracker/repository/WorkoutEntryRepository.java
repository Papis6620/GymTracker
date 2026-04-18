package com.papis.gymtracker.repository;

import com.papis.gymtracker.model.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
    List<WorkoutEntry> findByExerciseIdAndSessionUserIdOrderBySessionSessionDateAsc(String exerciseId, Long userId);
    List<WorkoutEntry> findByExerciseIdAndSessionUserId(String exerciseId, Long id);
    List<WorkoutEntry> findBySessionUserId(Long userId);
}
