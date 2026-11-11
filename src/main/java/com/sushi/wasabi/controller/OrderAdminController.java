package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.ApiResponse;
import com.sushi.wasabi.dto.ChangeOrderStatusDto;
import com.sushi.wasabi.dto.OrderAdminDto;
import com.sushi.wasabi.dto.OrderItemAdminDto;
import com.sushi.wasabi.enums.OrderStatus;
import com.sushi.wasabi.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@Tag(name = "Admin Orders", description = "Operations for managing customer orders")
public class OrderAdminController {

    private final OrderService orderService;

    @Operation(
            summary = "Get Orders",
            description = "Returns all orders to admin"
    )
    @GetMapping
    public List<OrderAdminDto> getAllOrders() {
        return orderService.getAllOrdersForAdmin();
    }

    @Operation(
            summary = "Get Order Id",
            description = "Returns specific order to admin"
    )
    @GetMapping("/{orderId}")
    public OrderAdminDto getOrderDetails(@PathVariable Long orderId
    ){
        return orderService.getOrderDetails(orderId);
    }

    @Operation(
            summary = "Put Status",
            description = "Updates specific order status"
    )
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
