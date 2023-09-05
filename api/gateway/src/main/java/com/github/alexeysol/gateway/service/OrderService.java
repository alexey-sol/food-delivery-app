package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.constant.ResourceConstant;
import com.github.alexeysol.common.model.dto.CreateOrderDto;
import com.github.alexeysol.common.model.dto.OrderDto;
import com.github.alexeysol.common.model.dto.UpdateOrderDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final GatewayConfig config;

    public List<OrderDto> getAllOrdersByUserId(String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.ORDER)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<OrderDto>>() {})
            .block();
    }

    public OrderDto createOrder(CreateOrderDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(ResourceConstant.ORDER).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(OrderDto.class)
            .block();
    }

    public OrderDto updateOrder(long id, UpdateOrderDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(ResourceConstant.ORDER, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(OrderDto.class)
            .block();
    }
}
