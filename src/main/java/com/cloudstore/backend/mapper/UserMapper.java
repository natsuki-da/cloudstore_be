package com.cloudstore.backend.mapper;

import com.cloudstore.backend.dto.UserRequestDTO;
import com.cloudstore.backend.dto.UserResponseDTO;
import com.cloudstore.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromDto (UserRequestDTO request){
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public UserResponseDTO toDto(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public User updateEntity(User user, UserRequestDTO request){
        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }
        if (request.email()!= null) {
            user.setEmail(request.email());
        }
        if (request.password()!= null) {
            user.setPassword(request.password());
        }
        return user;
    }
}
