package com.cloudstore.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
        @NotNull
        Long productId,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        @Positive
        BigDecimal price
) {
}
