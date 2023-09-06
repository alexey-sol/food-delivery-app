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
@RequestMapping(value = "/order", produces = "application/json")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrdersByUserId(HttpServletRequest request) {
        return orderService.getAllOrdersByUserId(request.getQueryString());
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderDto dto) {
        return orderService.createOrder(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderDto updateOrder(@PathVariable long id, @RequestBody UpdateOrderDto dto) {
        return orderService.updateOrder(id, dto);
    }
}
