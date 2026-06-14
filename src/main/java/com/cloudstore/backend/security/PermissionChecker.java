package com.cloudstore.backend.security;

import com.cloudstore.backend.model.Order;
import com.cloudstore.backend.model.User;
import com.cloudstore.backend.repository.OrderRepository;
import com.cloudstore.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PermissionChecker {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PermissionChecker(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public boolean isOwnerOrAdmin (Long orderId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin){
            return true;
        }
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("No order with this ID can be found."));
        return order.getUser().getEmail().equals(authentication.getName());
    }

    public boolean isOwner (Long orderId, Authentication authentication){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("No order with this ID can be found."));
        return order.getUser().getEmail().equals(authentication.getName());
    }

    public boolean isUserOwnerOrAdmin(Long userId, Authentication authentication){
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin){
            return true;
        }
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No user with this ID can be found"));
        return user.getEmail().equals(authentication.getName());
    }
}
