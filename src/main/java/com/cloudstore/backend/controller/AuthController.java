package com.cloudstore.backend.controller;

import com.cloudstore.backend.dto.LoginRequestDTO;
import com.cloudstore.backend.dto.LoginResponseDTO;
import com.cloudstore.backend.dto.UserRequestDTO;
import com.cloudstore.backend.dto.UserResponseDTO;
import com.cloudstore.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
