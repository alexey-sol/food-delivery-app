package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import com.github.alexeysol.common.feature.cart.model.dto.SaveCartItemDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final GatewayConfig config;

    public List<CartDto> getCartsByUserId(long userId, String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> getUserCartUri(builder, userId)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<CartDto>>() {})
            .block();
    }

    public CartDto saveCartItem(long userId, @RequestBody SaveCartItemDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> getUserCartUri(builder, userId).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(CartDto.class)
            .block();
    }

    private UriBuilder getUserCartUri(UriBuilder builder, long userId) {
        return builder.pathSegment(ResourceConstant.USER, String.valueOf(userId), ResourceConstant.CART);
    }
}
