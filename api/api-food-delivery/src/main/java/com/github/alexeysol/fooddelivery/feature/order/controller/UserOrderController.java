package com.github.alexeysol.fooddelivery.feature.order.controller;

import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.fooddelivery.feature.cart.service.CartService;
import com.github.alexeysol.fooddelivery.feature.order.mapper.OrderMapper;
import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.fooddelivery.feature.order.service.OrderService;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user/{userId}/order", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class UserOrderController {
    private final static String ORDER = "Order";
    private final static String USER = "User";

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
            var message = String.format(ErrorMessageConstant.ALREADY_EXISTS, ORDER);
            throw new ResponseStatusException(HttpStatus.CONFLICT, message);
        }

        var user = userService.findUserById(userId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER, userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
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
