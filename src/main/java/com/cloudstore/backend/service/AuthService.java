package com.cloudstore.backend.service;

import com.cloudstore.backend.dto.LoginRequestDTO;
import com.cloudstore.backend.dto.LoginResponseDTO;
import com.cloudstore.backend.dto.UserRequestDTO;
import com.cloudstore.backend.dto.UserResponseDTO;
import com.cloudstore.backend.model.User;
import com.cloudstore.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Register a user
    public UserResponseDTO register(UserRequestDTO request){
        Optional<User> existingUser = userRepository
                .findUserByEmail(request.email());
        if (existingUser.isPresent()){
            throw new IllegalArgumentException("User with this email address already exists");
        }
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .build();
        user = userRepository.save(user);
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    //login
    public LoginResponseDTO login (LoginRequestDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return LoginResponseDTO.builder().token(token).build();
    }

}
