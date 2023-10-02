package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final GatewayConfig config;

    public OrderDto updateOrder(long id, UpdateOrderDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> getOrderUri(builder, id).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(OrderDto.class)
            .block();
    }

    private UriBuilder getOrderUri(UriBuilder builder, long id) {
        return builder.pathSegment(ResourceConstant.ORDER, String.valueOf(id));
    }

    public List<OrderDto> getOrdersByUserId(long userId, String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> getUserOrderUri(builder, userId)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<OrderDto>>() {})
            .block();
    }

    public OrderDto createOrder(long userId, CreateOrderDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> getUserOrderUri(builder, userId).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(OrderDto.class)
            .block();
    }

    private UriBuilder getUserOrderUri(UriBuilder builder, long userId) {
        return builder.pathSegment(ResourceConstant.USER, String.valueOf(userId), ResourceConstant.ORDER);
    }
}
