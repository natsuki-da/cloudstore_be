package com.cloudstore.backend.controller;

import com.cloudstore.backend.dto.OrderItemRequestDTO;
import com.cloudstore.backend.dto.OrderRequestDTO;
import com.cloudstore.backend.dto.OrderResponseDTO;
import com.cloudstore.backend.enums.OrderStatus;
import com.cloudstore.backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    //To send http-requests to controller
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    private OrderResponseDTO response;
    private OrderRequestDTO request;

    @BeforeEach
    public void arrange(){
        response = OrderResponseDTO.builder()
                .id(1L)
                .totalPrice(BigDecimal.valueOf(100))
                .status(OrderStatus.PENDING)
                .items(List.of())
                .build();

        request = new OrderRequestDTO(
                BigDecimal.valueOf(100),
                List.of(new OrderItemRequestDTO(1L, 1, BigDecimal.valueOf(100)))
        );
    }

    @Test
    @DisplayName("Should create a new order")
    void shouldCreateOrder() throws Exception {

        when(orderService.createAnOrder(any(OrderRequestDTO.class), anyString())).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .with(user("user").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(orderService).createAnOrder(any(OrderRequestDTO.class), anyString());
     }

}
