package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.enums.OrderStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;
import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders Controller", description = "List of Orders Endpoints")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok().body(orderService.createOrder(orderRequestDTO));
    }

    @Operation(summary = "Update an existing order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable("id") Long id,
            @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok().body(orderService.updateOrder(id, orderRequestDTO));
    }

    @Operation(summary = "Get a single order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @Operation(summary = "Get all orders with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Column to sort by", example = "createdAt")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Sort order (ASC or DESC)", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok().body(orderService.getAllOrders(pageModel));
    }

    @Operation(summary = "Get the current user's orders")
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {
        return ResponseEntity.ok().body(orderService.getMyOrders());
    }

    @Operation(summary = "Cancel the current user's order")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelMyOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(orderService.cancelMyOrder(id));
    }

    @Operation(summary = "Get all orders for a restaurant (owner/admin only)")
    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRestaurant(
            @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity.ok().body(orderService.getOrdersByRestaurant(restaurantId));
    }

    @Operation(summary = "Get order statistics for a restaurant (owner/admin only)")
    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @GetMapping("/restaurant/{restaurantId}/stats")
    public ResponseEntity<Map<String, Object>> getRestaurantStats(
            @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity.ok().body(orderService.getRestaurantStats(restaurantId));
    }

    @Operation(summary = "Update order status (owner/admin only)")
    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") OrderStatus status) {
        return ResponseEntity.ok().body(orderService.updateOrderStatus(id, status));
    }

    @Operation(summary = "Delete an order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
