package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.gateway.config.GatewayConfig;
import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.CreateStoreDto;
import com.github.alexeysol.common.model.dto.StoreDto;
import com.github.alexeysol.common.model.dto.UpdateStoreDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
@RequestMapping(value = "/store", produces = "application/json")
@RequiredArgsConstructor
public class StoreController {
    private final static String STORE_RESOURCE = "store";

    private final GatewayConfig config;

    @GetMapping
    public ServicePage<StoreDto> getStores(HttpServletRequest request) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(STORE_RESOURCE)
                .query(request.getQueryString())
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<StoreDto>>() {})
            .block();
    }

    @GetMapping("/{id}")
    public StoreDto getStoreById(@PathVariable long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(STORE_RESOURCE, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(StoreDto.class)
            .block();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StoreDto createStore(@RequestBody CreateStoreDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(STORE_RESOURCE).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(StoreDto.class)
            .block();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public StoreDto updateStoreById(@RequestBody UpdateStoreDto dto, @PathVariable long id) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(STORE_RESOURCE, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(StoreDto.class)
            .block();
    }
}
