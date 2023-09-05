package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.constant.ResourceConstant;
import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.CreateProductDto;
import com.github.alexeysol.common.model.dto.ProductDto;
import com.github.alexeysol.common.model.dto.UpdateProductDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final GatewayConfig config;

    public ServicePage<ProductDto> getProducts(String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.PRODUCT)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<ProductDto>>() {})
            .block();
    }

    public ProductDto getProductById(long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.PRODUCT, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    public ProductDto createProduct(CreateProductDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(ResourceConstant.PRODUCT).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    public ProductDto updateProductById(long id, UpdateProductDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(ResourceConstant.PRODUCT, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }
}
