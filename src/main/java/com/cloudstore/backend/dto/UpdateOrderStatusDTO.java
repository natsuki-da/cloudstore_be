package com.cloudstore.backend.dto;

import com.cloudstore.backend.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDTO(
        @NotNull
        OrderStatus status
) {
}
