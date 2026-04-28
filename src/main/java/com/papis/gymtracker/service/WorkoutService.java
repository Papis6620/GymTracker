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
import jakarta.transaction.Transactional;
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

    public WorkoutSessionResponse getSessionById(Long id){
        User user = userService.getCurrentUser();

        return workoutSessionRepository.findByUserIdAndId(user.getId(), id)
                .map(WorkoutSessionResponse::from)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    @Transactional
    public void deleteSession(Long id){
        User user = userService.getCurrentUser();

        int count = workoutSessionRepository.deleteByUserIdAndId(user.getId(), id);
        if(count == 0){
            throw new RuntimeException("Session not found");
        }
    }

    @Transactional
    public void deleteEntry(Long sessionId, Long entryId){
        User user = userService.getCurrentUser();

        WorkoutSession session = workoutSessionRepository.findByUserIdAndId(user.getId(), sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        int count = workoutEntryRepository.deleteByIdAndSessionId(entryId, session.getId());

        if(count == 0){
            throw new RuntimeException("Entry not found");
        }
    }

    public WorkoutSessionResponse updateWorkoutEntry(Long sessionId, Long entryId, WorkoutEntryRequest request){
        User user = userService.getCurrentUser();

        WorkoutSession session = workoutSessionRepository.findByUserIdAndId(user.getId(), sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        WorkoutEntry entry = workoutEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if(!entry.getSession().getId().equals(session.getId())){
            throw new RuntimeException("You are not authorized to update this entry");
        }

        entry.setSets(request.sets());
        entry.setReps(request.reps());
        entry.setWeightKg(request.weightKg());
        workoutEntryRepository.save(entry);

        return WorkoutSessionResponse.from(session);
    }

}
