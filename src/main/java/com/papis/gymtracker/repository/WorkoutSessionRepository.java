package com.papis.gymtracker.repository;

import com.papis.gymtracker.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    List<WorkoutSession> findByUserId(Long userId);
    Optional<WorkoutSession> findByIdAndSessionDate(Long userId, LocalDate date);
    Optional<WorkoutSession> findByUserIdAndId(Long userId, Long id);
    int deleteByUserIdAndId(Long userId, Long id);
}
