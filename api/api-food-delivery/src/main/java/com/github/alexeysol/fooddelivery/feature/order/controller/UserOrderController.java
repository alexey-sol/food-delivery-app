package com.github.alexeysol.fooddelivery.feature.order.controller;

import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.fooddelivery.feature.cart.service.CartService;
import com.github.alexeysol.common.feature.order.exception.OrderAlreadyExistsException;
import com.github.alexeysol.fooddelivery.feature.order.mapper.OrderMapper;
import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.fooddelivery.feature.order.service.OrderService;
import com.github.alexeysol.common.feature.user.exception.UserNotFoundException;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user/{userId}/order", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class UserOrderController {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto> getAllOrdersByUserId(@PathVariable long userId) {
        var orders = orderService.findAllOrdersByUserId(userId);
        return orderMapper.map(orders);
    }

    @PostMapping
    public OrderDto createOrder(@PathVariable long userId, @RequestBody @Valid CreateOrderDto dto) {
        if (orderService.hasActiveOrderByUserIdAndPlaceId(userId, dto.getPlaceId())) {
            throw new OrderAlreadyExistsException();
        }

        var user = userService.findUserById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        var order = orderMapper.map(dto, user, new Order());

        long totalPrice = 0;

         for (var item : order.getOrderItems()) {
            totalPrice += item.getQuantity() * item.getProduct().getPrice();
        }

        order.setTotalPrice(totalPrice);
        order.getOrderItems().forEach(item -> item.setOrder(order));
        orderService.save(order);

        var optionalCart = cartService.findCartByUserIdAndPlaceId(userId, dto.getPlaceId());
        optionalCart.ifPresent(cart -> cartService.deleteCartById(cart.getId()));

        return orderMapper.map(order);
    }
}
