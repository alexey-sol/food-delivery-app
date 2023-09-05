package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.model.dto.CreateOrderDto;
import com.github.alexeysol.common.model.dto.OrderDto;
import com.github.alexeysol.common.model.dto.UpdateOrderDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@RequiredArgsConstructor
public class OrderController {
    private final static String ORDER_RESOURCE = "order";

    private final GatewayConfig config;

    @GetMapping
    public List<OrderDto> getAllOrdersByUserId(HttpServletRequest request) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ORDER_RESOURCE)
                .query(request.getQueryString())
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<OrderDto>>() {})
            .block();
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(ORDER_RESOURCE).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(OrderDto.class)
            .block();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderDto updateOrder(@PathVariable long id, @RequestBody UpdateOrderDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(ORDER_RESOURCE, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(OrderDto.class)
            .block();
    }
}
