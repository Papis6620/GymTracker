package com.papis.gymtracker.service;

import com.papis.gymtracker.dto.ProgressResponse;
import com.papis.gymtracker.model.User;
import com.papis.gymtracker.repository.WorkoutEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {
    private final WorkoutEntryRepository workoutEntryRepository;
    private final UserService userService;

    public List<ProgressResponse> getExerciseProgress(String exerciseId){
        User user = userService.getCurrentUser();

        return workoutEntryRepository.findByExerciseIdAndSessionUserIdOrderBySessionSessionDateAsc(exerciseId, user.getId())
                .stream()
                .map(ProgressResponse::from)
                .toList();
    }

}
