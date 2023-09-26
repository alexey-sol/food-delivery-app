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
@RequestMapping(value = "/product", produces = "application/json")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ServicePage<ProductPreviewDto> getProducts(HttpServletRequest request) {
        return productService.getProducts(request.getQueryString());
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductDto createProduct(@RequestBody CreateProductDto dto) {
        return productService.createProduct(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ProductDto updateProductById(@PathVariable long id, @RequestBody UpdateProductDto dto) {
        return productService.updateProductById(id, dto);
    }
}
