package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.ProductMapper;
import com.github.alexeysol.app.model.dto.CreateProductDto;
import com.github.alexeysol.app.model.dto.ProductDto;
import com.github.alexeysol.app.model.entity.Product;
import com.github.alexeysol.app.service.ProductService;
import com.github.alexeysol.app.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final StoreService storeService;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        var product = productService.findProductById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, "Product", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return productMapper.map(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid CreateProductDto dto) {
        var storeId = dto.getStoreId();

        var store = storeService.findStoreById(storeId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, "Store", storeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Product product = productMapper.map(dto, store);
        productService.saveProduct(product);
        return productMapper.map(product);
    }
}
