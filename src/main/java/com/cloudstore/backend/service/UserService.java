package com.cloudstore.backend.service;

import com.cloudstore.backend.config.SecurityConfig;
import com.cloudstore.backend.dto.UserRequestDTO;
import com.cloudstore.backend.dto.UserResponseDTO;
import com.cloudstore.backend.mapper.UserMapper;
import com.cloudstore.backend.model.User;
import com.cloudstore.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //To get all users from DB (ADMIN)
    public List<UserResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userMapper.toDto(user))
                .toList();
    }

    //To get one user by ID from DB
    public UserResponseDTO findUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID cannot be found"));
        return userMapper.toDto(user);
    }

    //Update an existing user
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID cannot be found"));
        User updatedUser = userMapper.updateEntity(user, request);
        return userMapper.toDto(updatedUser);
    }

}
