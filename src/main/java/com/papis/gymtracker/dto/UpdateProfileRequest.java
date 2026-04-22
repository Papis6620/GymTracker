package com.papis.gymtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record UpdateProfileRequest(
        @NotBlank String displayName
) {}
