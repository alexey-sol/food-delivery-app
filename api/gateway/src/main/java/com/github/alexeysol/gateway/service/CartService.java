package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.constant.ResourceConstant;
import com.github.alexeysol.common.model.dto.CartDto;
import com.github.alexeysol.common.model.dto.SaveCartItemDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final GatewayConfig config;

    public List<CartDto> getAllCartsByUserId(String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.CART)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<CartDto>>() {})
            .block();
    }

    public CartDto saveCartItem(@RequestBody SaveCartItemDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(ResourceConstant.CART).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(CartDto.class)
            .block();
    }
}
