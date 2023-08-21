package com.github.alexeysol.apigateway.controller;

import com.github.alexeysol.apigateway.config.ApiGatewayConfig;
import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.CreateProductDto;
import com.github.alexeysol.common.model.dto.ProductDto;
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

    private final ApiGatewayConfig config;

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
}
