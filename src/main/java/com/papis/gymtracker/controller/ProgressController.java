package com.papis.gymtracker.controller;

import com.papis.gymtracker.dto.ProgressResponse;
import com.papis.gymtracker.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/{exerciseId}")
    public List<ProgressResponse> getExerciseProgress(
            @PathVariable String exerciseId
    ){
        return progressService.getExerciseProgress(exerciseId);
    }
}
