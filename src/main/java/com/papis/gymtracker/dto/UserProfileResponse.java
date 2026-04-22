package com.papis.gymtracker.dto;

import com.papis.gymtracker.model.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserProfileResponse(
        Long id,
        String email,
        String displayName,
        LocalDateTime createdAt
) {
    public static UserProfileResponse from(User user){
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
