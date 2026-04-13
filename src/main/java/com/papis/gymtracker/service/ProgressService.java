package com.papis.gymtracker.service;

import com.papis.gymtracker.dto.ProgressResponse;
import com.papis.gymtracker.model.User;
import com.papis.gymtracker.repository.UserRepository;
import com.papis.gymtracker.repository.WorkoutEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {
    private final WorkoutEntryRepository workoutEntryRepository;
    private final UserRepository userRepository;

    public List<ProgressResponse> getExerciseProgress(String exerciseId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return workoutEntryRepository.findByExerciseIdAndSessionUserIdOrderBySessionSessionDateAsc(exerciseId, user.getId())
                .stream()
                .map(ProgressResponse::from)
                .toList();
    }

}
