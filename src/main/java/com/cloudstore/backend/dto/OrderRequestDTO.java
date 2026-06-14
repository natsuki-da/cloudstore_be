package com.cloudstore.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        @NotNull
        @Positive
        BigDecimal totalPrice,

        @NotNull
        List<OrderItemRequestDTO> items
        ) {
}
