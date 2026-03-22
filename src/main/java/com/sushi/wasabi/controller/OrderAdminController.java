package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.ApiResponse;
import com.sushi.wasabi.dto.ChangeOrderStatusDto;
import com.sushi.wasabi.dto.OrderAdminDto;
import com.sushi.wasabi.dto.OrderItemAdminDto;
import com.sushi.wasabi.enums.OrderStatus;
import com.sushi.wasabi.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderAdminDto> getAllOrders() {
        return orderService.getAllOrdersForAdmin();
    }

    @GetMapping("/{orderId}")
    public OrderAdminDto getOrderDetails(@PathVariable Long orderId
    ){
        return orderService.getOrderDetails(orderId);
    }


    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Void>> changeOrderStatus(@PathVariable Long orderId,
                                                               @RequestBody ChangeOrderStatusDto dto
                                                               ){
        OrderStatus orderStatus = OrderStatus.valueOf(dto.getStatus());
        orderService.changeOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Order status changed successfully", null)
        );

    }
}
