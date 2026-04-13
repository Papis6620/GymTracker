package com.papis.gymtracker.controller;

import com.papis.gymtracker.dto.WorkoutEntryRequest;
import com.papis.gymtracker.dto.WorkoutSessionRequest;
import com.papis.gymtracker.dto.WorkoutSessionResponse;
import com.papis.gymtracker.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutSessionResponse> createSession(
            @Valid @RequestBody WorkoutSessionRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                workoutService.createWorkoutSession(request)
        );
    }

    @PostMapping("/{sessionId}/entries")
    public ResponseEntity<WorkoutSessionResponse> addEntry(
            @PathVariable Long sessionId,
            @Valid @RequestBody WorkoutEntryRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                workoutService.addEntry(sessionId, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<WorkoutSessionResponse>> getUserSessions(){
        return ResponseEntity.ok(workoutService.getUserSessions());
    }
}
