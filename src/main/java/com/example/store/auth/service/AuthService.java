package com.example.store.auth.service;

import com.example.store.auth.jwt.JwtService;
import com.example.store.auth.util.AuthResponse;
import com.example.store.auth.util.LoginRequest;
import com.example.store.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public record AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
