package com.cloudstore.backend.mapper;

import com.cloudstore.backend.dto.OrderItemRequestDTO;
import com.cloudstore.backend.model.OrderItem;
import lombok.Builder;
import org.springframework.stereotype.Component;


@Component
public class OrderItemMapper {

    public OrderItem fromDto (OrderItemRequestDTO request){
        return OrderItem.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .price(request.price())
                .build();
    }

}

