package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.model.dto.CartDto;
import com.github.alexeysol.common.model.dto.SaveCartItemDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@RestController
@RequestMapping(value = "/cart", produces = "application/json")
@RequiredArgsConstructor
public class CartController {
    private final static String CART_RESOURCE = "cart";

    private final GatewayConfig config;

    @GetMapping
    public List<CartDto> getAllCartsByUserId(HttpServletRequest request) {
        return config.appWebClient()
            .get()
//            .uri(builder -> builder.pathSegment(CART_RESOURCE, String.valueOf(userId)).build())
            .uri(builder -> builder.pathSegment(CART_RESOURCE)
                .query(request.getQueryString())
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<CartDto>>() {})
            .block();
    }

    @PatchMapping
    public CartDto saveCartItem(@RequestBody SaveCartItemDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(CART_RESOURCE).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(CartDto.class)
            .block();
    }
}
