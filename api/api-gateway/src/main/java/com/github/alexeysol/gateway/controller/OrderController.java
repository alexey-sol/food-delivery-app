package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import com.github.alexeysol.gateway.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/order/{id}")
    public OrderDto updateOrder(@PathVariable long id, @RequestBody UpdateOrderDto dto) {
        return orderService.updateOrder(id, dto);
    }

    @GetMapping("/user/{userId}/order")
    public List<OrderDto> getAllOrdersByUserId(@PathVariable long userId, HttpServletRequest request) {
        return orderService.getAllOrdersByUserId(userId, request.getQueryString());
    }

    @PostMapping("/user/{userId}/order")
    public OrderDto createOrder(@PathVariable long userId, @RequestBody CreateOrderDto dto) {
        return orderService.createOrder(userId, dto);
    }
}
