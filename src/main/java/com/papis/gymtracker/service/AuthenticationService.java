package com.papis.gymtracker.service;

import com.papis.gymtracker.config.JwtService;
import com.papis.gymtracker.dto.AuthenticationRequest;
import com.papis.gymtracker.dto.AuthenticationResponse;
import com.papis.gymtracker.dto.RegisterRequest;
import com.papis.gymtracker.model.User;
import com.papis.gymtracker.model.enums.Role;
import com.papis.gymtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        if(userRepository.findByEmail(request.email()).isPresent())
            throw new RuntimeException("Email already in use");

        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .displayName(request.displayName())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var token = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(token);
    }
}
