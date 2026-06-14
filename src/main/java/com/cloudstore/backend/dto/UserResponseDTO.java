package com.cloudstore.backend.dto;

import com.cloudstore.backend.enums.Role;
import lombok.Builder;

@Builder
public record UserResponseDTO (Long id, String firstName, String lastName, String email, Role role) {

}
