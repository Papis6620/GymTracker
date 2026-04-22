package com.papis.gymtracker.controller;

import com.papis.gymtracker.dto.ChangePasswordRequest;
import com.papis.gymtracker.dto.UpdateProfileRequest;
import com.papis.gymtracker.dto.UserProfileResponse;
import com.papis.gymtracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile(){
        return ResponseEntity.ok(UserProfileResponse.from(userService.getCurrentUser()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request
    ){
        return ResponseEntity.ok(UserProfileResponse.from(userService.updateDisplayName(request)));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ){
        userService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount(){
        userService.deleteAccount();
        return ResponseEntity.noContent().build();
    }


}
