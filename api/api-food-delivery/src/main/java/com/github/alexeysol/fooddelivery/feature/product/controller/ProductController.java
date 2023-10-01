package com.github.alexeysol.fooddelivery.feature.product.controller;

import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductPreviewDto;
import com.github.alexeysol.common.feature.product.model.dto.UpdateProductDto;
import com.github.alexeysol.common.feature.product.exception.ProductNotFoundException;
import com.github.alexeysol.fooddelivery.feature.product.mapper.ProductMapper;
import com.github.alexeysol.fooddelivery.feature.product.mapper.ProductPreviewMapper;
import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.github.alexeysol.fooddelivery.feature.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductPreviewMapper productPreviewMapper;

    @GetMapping
    public Page<ProductPreviewDto> getProducts(
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.findAllProducts(pageable);
        return productPreviewMapper.map(productPage, pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        var product = productService.findProductById(id).orElseThrow(() -> {
            throw new ProductNotFoundException();
        });

        return productMapper.map(product);
    }

    @PatchMapping("/{id}")
    public ProductDto updateProductById(@PathVariable long id, @RequestBody @Valid UpdateProductDto dto) {
        var product = productService.findProductById(id).orElseThrow(() -> {
            throw new ProductNotFoundException();
        });

        Product updatedProduct = productMapper.map(dto, product);
        productService.saveProduct(updatedProduct);
        return productMapper.map(updatedProduct);
    }
}
