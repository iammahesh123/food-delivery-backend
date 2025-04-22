package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.OrderService;
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

    @PostMapping
    ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok().body(orderService.createOrder(orderRequestDTO));
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok().body(orderService.updateOrder(id, orderRequestDTO));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @GetMapping
    ResponseEntity<List<OrderResponseDTO>> getAllOrders(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok().body(orderService.getAllOrders(pageModel));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
