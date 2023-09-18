package com.github.alexeysol.app.feature.order.controller;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.app.feature.order.mapper.OrderMapper;
import com.github.alexeysol.app.feature.order.model.entity.Order;
import com.github.alexeysol.app.feature.cart.service.CartService;
import com.github.alexeysol.app.feature.order.service.OrderService;
import com.github.alexeysol.app.feature.user.service.UserService;
import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final static String ORDER = "Order";
    private final static String USER = "User";

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    private final OrderMapper orderMapper;

    @GetMapping  // TODO query better: it's explicit
    public List<OrderDto> getAllOrdersByUserId(@RequestParam long userId) {
        var orders = orderService.findAllOrdersByUserId(userId);
        return orderMapper.map(orders);
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody @Valid CreateOrderDto dto) {
        if (orderService.hasActiveOrderByUserIdAndPlaceId(dto.getUserId(), dto.getPlaceId())) {
            var message = String.format(ErrorMessageConstant.ALREADY_EXISTS, ORDER);
            throw new ResponseStatusException(HttpStatus.CONFLICT, message);
        }

        var userId = dto.getUserId();

        var user = userService.findUserById(userId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER, userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        // TODO place exists? or else 404


//        var order = orderMapper.map(dto, user);
//        var order = new Order();
        var order = orderMapper.map(dto, user, new Order());
//        orderMapper.map(dto, order);

        // TODO how to set orderItem.product with productId?
        long totalPrice = 0;

//        for (var item : order.getOrderItems()) {
//            totalPrice += item.getQuantity() * item.getProduct().getPrice();
//        }
                 for (var item : order.getOrderItems()) {
                    totalPrice += item.getQuantity() * item.getProduct().getPrice();
                }

//        long totalPrice = order.getOrderItems().stream().reduce(0L, item -> item.getQuantity() * item.getProduct().getPrice());

        order.setTotalPrice(totalPrice);

        order.getOrderItems().forEach(item -> {
            item.setOrder(order);
            // TODO set parent order in mapper somehow?

        });

        orderService.save(order);

        var optionalCart = cartService.findCartByUserIdAndPlaceId(dto.getUserId(), dto.getPlaceId());

        if (optionalCart.isPresent()) {
            cartService.deleteCartById(optionalCart.get().getId());
        }


        return orderMapper.map(order);
    }

    @PatchMapping("/{id}")
    public OrderDto updateOrder(@PathVariable long id, @RequestBody @Valid UpdateOrderDto dto) {
        var order = orderService.findOrderById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, ORDER, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var updatedOrder = orderMapper.map(dto, order);
        orderService.save(updatedOrder);
        return orderMapper.map(updatedOrder);
    }
}
