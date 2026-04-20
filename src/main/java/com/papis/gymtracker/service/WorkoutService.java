package com.papis.gymtracker.service;

import com.papis.gymtracker.dto.WorkoutEntryRequest;
import com.papis.gymtracker.dto.WorkoutSessionRequest;
import com.papis.gymtracker.dto.WorkoutSessionResponse;
import com.papis.gymtracker.model.Exercise;
import com.papis.gymtracker.model.User;
import com.papis.gymtracker.model.WorkoutEntry;
import com.papis.gymtracker.model.WorkoutSession;
import com.papis.gymtracker.repository.ExerciseRepository;
import com.papis.gymtracker.repository.WorkoutEntryRepository;
import com.papis.gymtracker.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutEntryRepository workoutEntryRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserService userService;

    public WorkoutSessionResponse createWorkoutSession(WorkoutSessionRequest request){
        User user = userService.getCurrentUser();

        WorkoutSession session = WorkoutSession.builder()
                        .user(user)
                        .sessionDate(request.sessionDate())
                        .notes(request.notes())
                        .build();

        WorkoutSession savedSession = workoutSessionRepository.save(session);
        return WorkoutSessionResponse.from(savedSession);
    }

    public WorkoutSessionResponse addEntry(Long sessionId, WorkoutEntryRequest request){
        User user = userService.getCurrentUser();
        WorkoutSession session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if(!session.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not authorized to add an entry to this session");
        }

        Exercise exercise = exerciseRepository.findById(request.exerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        WorkoutEntry workoutEntry = WorkoutEntry.builder()
                        .session(session)
                        .exercise(exercise)
                        .sets(request.sets())
                        .reps(request.reps())
                        .weightKg(request.weightKg())
                        .build();

        workoutEntryRepository.save(workoutEntry);
        return WorkoutSessionResponse.from(session);
    }

    public List<WorkoutSessionResponse> getUserSessions(){
        User user = userService.getCurrentUser();
        return workoutSessionRepository.findByUserId(user.getId())
                .stream()
                .map(WorkoutSessionResponse::from)
                .toList();
    }
}
