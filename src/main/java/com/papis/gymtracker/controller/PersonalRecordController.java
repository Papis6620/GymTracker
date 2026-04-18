package com.papis.gymtracker.controller;

import com.papis.gymtracker.dto.PersonalRecordResponse;
import com.papis.gymtracker.service.PersonalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class PersonalRecordController {
    private final PersonalRecordService personalRecordService;

    @GetMapping("/{exerciseId}")
    public ResponseEntity<PersonalRecordResponse> getExerciseRecord(
            @PathVariable String exerciseId
    ){
        return ResponseEntity.ok(personalRecordService.getExerciseRecord(exerciseId));
    }
}
