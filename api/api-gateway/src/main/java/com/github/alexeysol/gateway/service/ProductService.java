package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.feature.product.model.dto.CreateProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductPreviewDto;
import com.github.alexeysol.common.feature.product.model.dto.UpdateProductDto;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.common.shared.model.ServicePage;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final GatewayConfig config;

    public ServicePage<ProductPreviewDto> getProducts(String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> getProductUri(builder)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<ProductPreviewDto>>() {})
            .block();
    }

    public ProductDto getProductById(long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> getProductUri(builder, id).build())
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    public ProductDto updateProductById(long id, UpdateProductDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> getProductUri(builder, id).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    private UriBuilder getProductUri(UriBuilder builder) {
        return builder.pathSegment(ResourceConstant.PRODUCT);
    }

    private UriBuilder getProductUri(UriBuilder builder, long id) {
        return builder.pathSegment(ResourceConstant.PRODUCT, String.valueOf(id));
    }

    public ServicePage<ProductPreviewDto> getProductsByPlaceId(long placeId, String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> getPlaceProductUri(builder, placeId)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<ProductPreviewDto>>() {})
            .block();
    }

    public ProductDto createProduct(long placeId, CreateProductDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> getPlaceProductUri(builder, placeId).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(ProductDto.class)
            .block();
    }

    private UriBuilder getPlaceProductUri(UriBuilder builder, long placeId) {
        return builder.pathSegment(ResourceConstant.PLACE, String.valueOf(placeId), ResourceConstant.PRODUCT);
    }
}
