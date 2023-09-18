package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.CreateProductDto;
import com.github.alexeysol.common.model.dto.ProductDto;
import com.github.alexeysol.common.model.dto.UpdateProductDto;
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
    public ServicePage<ProductDto> getProducts(HttpServletRequest request) {
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
