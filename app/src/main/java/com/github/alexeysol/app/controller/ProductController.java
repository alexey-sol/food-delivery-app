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

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private static final String PRODUCT = "Product";
    private static final String STORE = "Store";

    private final ProductService productService;
    private final StoreService storeService;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @GetMapping
    public Set<ProductDto> getProducts(@RequestParam Optional<Long> storeId) {
        Set<Product> products;

        if (storeId.isPresent()) {
            products = getAllProductsByStoreId(storeId.get());
        } else {
            products = getAllProducts();
        }

        return productMapper.map(products);
    }

    private Set<Product> getAllProductsByStoreId(long storeId) {
        storeService.findStoreById(storeId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, STORE, storeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return productService.findAllProductsByStoreId(storeId);
    }

    private Set<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        var product = productService.findProductById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PRODUCT, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return productMapper.map(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid CreateProductDto dto) {
        var storeId = dto.getStoreId();

        var store = storeService.findStoreById(storeId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, STORE, storeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Product product = productMapper.map(dto, store);
        productService.saveProduct(product);
        return productMapper.map(product);
    }
}
