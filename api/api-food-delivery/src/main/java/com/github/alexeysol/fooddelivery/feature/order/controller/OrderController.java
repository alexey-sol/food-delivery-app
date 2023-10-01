package com.github.alexeysol.fooddelivery.feature.order.controller;

import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import com.github.alexeysol.common.feature.order.exception.OrderNotFoundException;
import com.github.alexeysol.fooddelivery.feature.order.mapper.OrderMapper;
import com.github.alexeysol.fooddelivery.feature.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PatchMapping("/{id}")
    public OrderDto updateOrderById(@PathVariable long id, @RequestBody @Valid UpdateOrderDto dto) {
        var order = orderService.findOrderById(id).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        var updatedOrder = orderMapper.map(dto, order);
        orderService.save(updatedOrder);
        return orderMapper.map(updatedOrder);
    }
}
