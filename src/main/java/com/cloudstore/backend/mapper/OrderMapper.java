package com.cloudstore.backend.mapper;

import com.cloudstore.backend.dto.OrderRequestDTO;
import com.cloudstore.backend.dto.OrderResponseDTO;
import com.cloudstore.backend.model.Order;
import com.cloudstore.backend.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public Order fromDto (OrderRequestDTO request){
        List<OrderItem> orderItems = request.items()
                .stream()
                .map(orderItem -> orderItemMapper.fromDto(orderItem))
                .toList();

        Order order = Order.builder()
                .totalPrice(request.totalPrice())
                .items(orderItems)
                .build();
        return order;
    }

    public OrderResponseDTO toDto(Order order){
        return OrderResponseDTO.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .status(order.getStatus())
                .user(order.getUser())
                .items(order.getItems())
                .build();
    }
}
