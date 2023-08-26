package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.gateway.config.GatewayConfig;
import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
@RequiredArgsConstructor
public class ProductController {
    private final static String PRODUCT_RESOURCE = "product";

    private final GatewayConfig config;

    @GetMapping
    public ServicePage<ProductDto> getProducts(HttpServletRequest request) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(PRODUCT_RESOURCE)
                .query(request.getQueryString())
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<ProductDto>>() {})
            .block();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(PRODUCT_RESOURCE, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductDto createProduct(@RequestBody CreateProductDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(PRODUCT_RESOURCE).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ProductDto updateProductById(@PathVariable long id, @RequestBody UpdateProductDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(PRODUCT_RESOURCE, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }
}
