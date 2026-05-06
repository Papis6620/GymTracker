package com.papis.gymtracker.service;

import com.papis.gymtracker.dto.ChangePasswordRequest;
import com.papis.gymtracker.dto.UpdateProfileRequest;
import com.papis.gymtracker.exception.InvalidCredentialsException;
import com.papis.gymtracker.exception.ResourceNotFoundException;
import com.papis.gymtracker.model.User;
import com.papis.gymtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User updateDisplayName(UpdateProfileRequest request){
        User user = getCurrentUser();
        user.setDisplayName(request.displayName());
        return userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequest request){
        User user = getCurrentUser();
        if(!passwordEncoder.matches(request.currentPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public void deleteAccount(){
        User user = getCurrentUser();
        userRepository.delete(user);
    }
}
