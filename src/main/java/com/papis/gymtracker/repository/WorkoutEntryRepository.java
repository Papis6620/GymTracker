package com.papis.gymtracker.repository;

import com.papis.gymtracker.model.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
    List<WorkoutEntry> findByExerciseIdAndSessionUserIdOrderBySessionSessionDateAsc(String exerciseId, Long userId);
    List<WorkoutEntry> findByExerciseIdAndSessionUserId(String exerciseId, Long id);
    List<WorkoutEntry> findBySessionUserId(Long userId);
    Optional<WorkoutEntry> findByIdAndSessionId(Long entryId, Long sessionId);
    int deleteByIdAndSessionId(Long entryId, Long sessionId);
}
