package com.cloudstore.backend.controller;

import com.cloudstore.backend.dto.OrderRequestDTO;
import com.cloudstore.backend.dto.OrderResponseDTO;
import com.cloudstore.backend.dto.UpdateOrderStatusDTO;
import com.cloudstore.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders (){
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("@permissionChecker.isOwnerOrAdmin(#id, authentication)")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id){
        OrderResponseDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    //authentication.getName() generates an user's
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request, Authentication authentication){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createAnOrder(request, authentication.getName()));
    }

    @PreAuthorize("@permissionChecker.isOwner(#id, authentication)")
    //USER_ONLY Cancel an order if the status is still pending
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id){
        OrderResponseDTO cancel = orderService.cancelOrder(id);
        return ResponseEntity.ok(cancel);
    }

    //ADMIN_ONLY Update a status of an order
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusDTO status){
        OrderResponseDTO response = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
