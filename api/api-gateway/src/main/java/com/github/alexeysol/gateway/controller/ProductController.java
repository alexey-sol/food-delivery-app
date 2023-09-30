package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.feature.product.model.dto.ProductPreviewDto;
import com.github.alexeysol.common.shared.model.ServicePage;
import com.github.alexeysol.common.feature.product.model.dto.CreateProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.UpdateProductDto;
import com.github.alexeysol.gateway.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    public ServicePage<ProductPreviewDto> getProducts(HttpServletRequest request) {
        return productService.getProducts(request.getQueryString());
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/product/{id}")
    public ProductDto updateProductById(@PathVariable long id, @RequestBody UpdateProductDto dto) {
        return productService.updateProductById(id, dto);
    }

    @GetMapping("/place/{placeId}/product")
    public ServicePage<ProductPreviewDto> getProductsByPlaceId(@PathVariable long placeId, HttpServletRequest request) {
        return productService.getProductsByPlaceId(placeId, request.getQueryString());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/place/{placeId}/product")
    public ProductDto createProduct(@PathVariable long placeId, @RequestBody CreateProductDto dto) {
        return productService.createProduct(placeId, dto);
    }
}
