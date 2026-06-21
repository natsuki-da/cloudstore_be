package com.cloudstore.backend.service;

import com.cloudstore.backend.dto.LoginRequestDTO;
import com.cloudstore.backend.dto.LoginResponseDTO;
import com.cloudstore.backend.dto.UserRequestDTO;
import com.cloudstore.backend.dto.UserResponseDTO;
import com.cloudstore.backend.enums.Role;
import com.cloudstore.backend.model.User;
import com.cloudstore.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    private UserRequestDTO request;
    private User dbUser;
    private LoginRequestDTO loginRequest;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @BeforeEach
    public void arrange() {
        request = new UserRequestDTO(
                "Test",
                "User",
                "testPassword123",
                "test@test.com"
        );

        dbUser = User.builder()
                .id(1L)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password("encodedPassword")
                .email(request.email())
                .role(Role.USER)
                .build();

        loginRequest = new LoginRequestDTO(
                "testPassword123",
                "test@test.com"
        );
    }



    @Test
    @DisplayName("Should register a new user")
    void shouldRegisterNewUser(){
        when(userRepository.findUserByEmail("test@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testPassword123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(dbUser);

        UserResponseDTO response = authService.register(request);

        assertEquals(1L, response.id());
        assertEquals("Test", response.firstName());
        assertEquals("User", response.lastName());
        assertEquals("test@test.com", response.email());

        verify(userRepository).findUserByEmail("test@test.com");
        verify(passwordEncoder).encode("testPassword123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should login an existing user")
    void shouldLoginExistingUser(){
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(jwtService.generateToken(userDetails)).thenReturn("mock-jwt-token");

        LoginResponseDTO loginResponse = authService.login(loginRequest);

        assertEquals("mock-jwt-token", loginResponse.token());

        verify(authenticationManager).authenticate(any());
        verify(authentication).getPrincipal();
        verify(jwtService).generateToken(userDetails);
    }

}
