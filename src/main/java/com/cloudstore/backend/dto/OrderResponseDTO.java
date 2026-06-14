package com.cloudstore.backend.dto;

import com.cloudstore.backend.enums.OrderStatus;
import com.cloudstore.backend.model.OrderItem;
import com.cloudstore.backend.model.User;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDTO(
        Long id,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus status,
        User user,
        List<OrderItem> items) {
}
