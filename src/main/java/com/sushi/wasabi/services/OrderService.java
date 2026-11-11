package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.OrderAdminDto;
import com.sushi.wasabi.entity.Order;
import com.sushi.wasabi.enums.OrderStatus;
import com.sushi.wasabi.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderAdminDto> getAllOrdersForAdmin() {
        return orderRepository.findAllWithDetails()
                .stream()
                .map(Order::mapToAdminDto)
                .toList();
    }

    public OrderAdminDto getOrderDetails(Long orderId) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return order.mapToAdminDto();
    }

    public void changeOrderStatus(Long orderId, OrderStatus status){
        Order order = orderRepository.findByIdWithDetails(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);
    }

}
