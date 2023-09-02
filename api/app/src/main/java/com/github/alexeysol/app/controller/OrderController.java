package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.OrderMapper;
import com.github.alexeysol.app.model.entity.Order;
import com.github.alexeysol.app.model.entity.Product;
import com.github.alexeysol.app.service.CartService;
import com.github.alexeysol.app.service.OrderService;
import com.github.alexeysol.app.service.ProductService;
import com.github.alexeysol.app.service.UserService;
import com.github.alexeysol.common.model.dto.CreateOrderDto;
import com.github.alexeysol.common.model.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@RequiredArgsConstructor
public class OrderController {
    private final static String ORDER_RESOURCE = "Order";
    private final static String PRODUCT_RESOURCE = "Product";
    private final static String USER_RESOURCE = "User";

    private final CartService cartService;
    private final OrderService orderService;
//    private final ProductService productService;
    private final UserService userService;

//    private final Order

//    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private final OrderMapper orderMapper;

    @GetMapping  // TODO query better: it's explicit
    public List<OrderDto> getAllOrdersByUserId(@RequestParam long userId) {
        var orders = orderService.findAllOrdersByUserId(userId);
        return orderMapper.map(orders);
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderDto dto) {
        // TODO throw 409 if there's active order
        if (orderService.hasActiveOrderByUserIdAndStoreId(dto.getUserId(), dto.getStoreId())) {
            var message = String.format(ErrorMessageConstant.ALREADY_EXISTS, ORDER_RESOURCE);
            throw new ResponseStatusException(HttpStatus.CONFLICT, message);
        }

        var userId = dto.getUserId();

        var user = userService.findUserById(userId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER_RESOURCE, userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        // TODO store exists? or else 404


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

        var optionalCart = cartService.findCartByUserIdAndStoreId(dto.getUserId(), dto.getStoreId());

        if (optionalCart.isPresent()) {
            cartService.deleteCartById(optionalCart.get().getId());
        }


        return orderMapper.map(order);
    }
}
