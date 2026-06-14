package com.cloudstore.backend.controller;

import com.cloudstore.backend.dto.UserRequestDTO;
import com.cloudstore.backend.dto.UserResponseDTO;
import com.cloudstore.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
   private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers (){
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("@permissionChecker.isUserOwnerOrAdmin(#id, authentication)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById (@PathVariable Long id) {
        UserResponseDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("@permissionChecker.isUserOwnerOrAdmin(#id, authentication)")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request){
        UserResponseDTO response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }
}
