package com.cloudstore.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Password field cannot be empty")
        @Size(max = 100)
        String password,

        @NotBlank(message = "Email field cannot be empty")
        @Email(message = "Invalid email address format.")
        @Size(max = 60)
        String email
){

}
