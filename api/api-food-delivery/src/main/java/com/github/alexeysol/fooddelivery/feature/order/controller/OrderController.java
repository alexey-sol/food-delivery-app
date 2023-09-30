package com.github.alexeysol.fooddelivery.feature.order.controller;

import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.fooddelivery.feature.order.mapper.OrderMapper;
import com.github.alexeysol.fooddelivery.feature.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final static String ORDER = "Order";

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PatchMapping("/{id}")
    public OrderDto updateOrderById(@PathVariable long id, @RequestBody @Valid UpdateOrderDto dto) {
        var order = orderService.findOrderById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, ORDER, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var updatedOrder = orderMapper.map(dto, order);
        orderService.save(updatedOrder);
        return orderMapper.map(updatedOrder);
    }
}
