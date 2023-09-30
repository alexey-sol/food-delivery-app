package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import com.github.alexeysol.common.feature.cart.model.dto.SaveCartItemDto;
import com.github.alexeysol.gateway.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user/{userId}/cart", produces = "application/json")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartDto> getCartsByUserId(@PathVariable("userId") long userId, HttpServletRequest request) {
        return cartService.getCartsByUserId(userId, request.getQueryString());
    }

    @PatchMapping
    public CartDto saveCartItem(@PathVariable("userId") long userId, @RequestBody SaveCartItemDto dto) {
        return cartService.saveCartItem(userId, dto);
    }
}
