package com.cloudstore.backend.service;

import com.cloudstore.backend.dto.OrderRequestDTO;
import com.cloudstore.backend.dto.OrderResponseDTO;
import com.cloudstore.backend.dto.UpdateOrderStatusDTO;
import com.cloudstore.backend.enums.OrderStatus;
import com.cloudstore.backend.mapper.OrderItemMapper;
import com.cloudstore.backend.mapper.OrderMapper;
import com.cloudstore.backend.model.Order;
import com.cloudstore.backend.model.OrderItem;
import com.cloudstore.backend.model.User;
import com.cloudstore.backend.repository.OrderRepository;
import com.cloudstore.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderService (OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, OrderItemMapper orderItemMapper){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.orderItemMapper = orderItemMapper;
    }

    //To get all orders from DB
    public List<OrderResponseDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderList = orders.stream().map(order -> orderMapper.toDto(order)).toList();
        return orderList;
    }

    //To get an order by ID from DB
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID cannot be found"));
        return orderMapper.toDto(order);
    }

    //ADMIN_ONLY Update a status of an order
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, UpdateOrderStatusDTO orderStatus){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID cannot be found"));
        order.setStatus(orderStatus.status());
        return orderMapper.toDto(order);
    }

    //USER_ONLY Cancel an existing order
    @Transactional
    public OrderResponseDTO cancelOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID cannot be found"));

        if (order.getStatus() != OrderStatus.PENDING){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Orders cannot be cancelled anymore");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toDto(order);
    }

    //ADMIN_ONLY Delete an existing order from DB
    //public OrderResponseDTO deleteOrder(Long id, OrderRequestDTO request){}

    //Create a new order
    @Transactional
    public OrderResponseDTO createAnOrder(OrderRequestDTO request, String email){
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID cannot be found"));
        Order order = Order.builder()
                .totalPrice(request.totalPrice())
                .user(user)
                .build();
        List<OrderItem> orderItems = request.items()
                .stream()
                .map(orderItem -> orderItemMapper.fromDto(orderItem))
                .peek(orderItem -> orderItem.setOrder(order))
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }
}