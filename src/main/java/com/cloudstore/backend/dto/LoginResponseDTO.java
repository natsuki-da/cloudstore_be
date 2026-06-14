package com.cloudstore.backend.dto;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
        String token
) { }