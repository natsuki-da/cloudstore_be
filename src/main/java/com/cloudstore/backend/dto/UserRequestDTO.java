package com.cloudstore.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO (
        @NotBlank(message = "First name field cannot be empty")
        @Size(max=50)
        String firstName,

        @NotBlank(message="Last name field cannot be empty")
        @Size(max=50)
        String lastName,

        @NotBlank(message = "Password field cannot be empty")
        @Size(max = 100)
        String password,

        @NotBlank(message = "Email field cannot be empty")
        @Email(message = "Invalid email address format.")
        @Size(max = 60)
        String email
){
}
