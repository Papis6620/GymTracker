package com.papis.gymtracker.dto;

import com.papis.gymtracker.model.WorkoutSession;

import java.time.LocalDate;
import java.util.List;

public record WorkoutSessionResponse(
        Long id,
        LocalDate sessionDate,
        String notes,
        List<WorkoutEntryResponse> entries
) {
    public static WorkoutSessionResponse from(WorkoutSession session){
        return new WorkoutSessionResponse(
                session.getId(),
                session.getSessionDate(),
                session.getNotes(),
                session.getEntries().stream()
                        .map(WorkoutEntryResponse::from)
                        .toList()
        );
    }
}

