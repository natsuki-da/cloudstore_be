package com.cloudstore.backend.service;

import com.cloudstore.backend.dto.OrderItemRequestDTO;
import com.cloudstore.backend.dto.OrderRequestDTO;
import com.cloudstore.backend.dto.OrderResponseDTO;
import com.cloudstore.backend.enums.Role;
import com.cloudstore.backend.model.User;
import com.cloudstore.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@ActiveProfiles("test")
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private OrderRequestDTO request;

    @BeforeEach
    void arrange() {
        user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@test.com")
                .password("testPassword123")
                .role(Role.USER)
                .build();

        user = userRepository.save(user);

        request = new OrderRequestDTO(
                BigDecimal.valueOf(100),
                List.of(new OrderItemRequestDTO(1L, 1, BigDecimal.valueOf(100)))
        );
    }

    @Test
    @DisplayName("Should create an order")
    void shouldCreateAnOrder(){

        OrderResponseDTO result = orderService.createAnOrder(request, user.getEmail());

        assertNotNull(result);
        assertNotNull(result.id());
        System.out.println(result.id());
    }
}
