package com.papis.gymtracker.service;

import com.papis.gymtracker.dto.PersonalRecordResponse;
import com.papis.gymtracker.model.User;
import com.papis.gymtracker.model.WorkoutEntry;
import com.papis.gymtracker.repository.UserRepository;
import com.papis.gymtracker.repository.WorkoutEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalRecordService {
    private final WorkoutEntryRepository workoutEntryRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public PersonalRecordResponse getExerciseRecord(String exerciseId){
        User user = getCurrentUser();

        List<WorkoutEntry> entries = workoutEntryRepository.findByExerciseIdAndSessionUserId(exerciseId, user.getId());
        return PersonalRecordResponse.from(entries);
    }

    public List<PersonalRecordResponse> getAllRecords(){
        User user = getCurrentUser();
        return workoutEntryRepository.
                findBySessionUserId(user.getId())
                .stream()
                .collect(Collectors.groupingBy(entry -> entry.getExercise().getId()))
                .values()
                .stream()
                .map(PersonalRecordResponse::from)
                .filter(Objects::nonNull)
                .toList();
    }
}
