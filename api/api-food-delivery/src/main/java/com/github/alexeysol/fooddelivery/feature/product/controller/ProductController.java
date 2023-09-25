package com.github.alexeysol.fooddelivery.feature.product.controller;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.fooddelivery.feature.product.mapper.ProductMapper;
import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.github.alexeysol.fooddelivery.feature.product.service.ProductService;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
import com.github.alexeysol.common.feature.product.model.dto.CreateProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.UpdateProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private static final String PRODUCT = "Product";
    private static final String PLACE = "Place";

    private final ProductService productService;
    private final PlaceService placeService;
    private final ProductMapper productMapper;

    @GetMapping
    public Page<ProductDto> getProducts(
        @RequestParam Optional<Long> placeId,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Product> productPage;

        if (placeId.isPresent()) {
            productPage = getAllProductsByPlaceId(placeId.get(), pageable);
        } else {
            productPage = getAllProducts(pageable);
        }

        return productMapper.map(productPage, pageable);
    }

    private Page<Product> getAllProductsByPlaceId(long placeId, Pageable pageable) {
        placeService.findPlaceById(placeId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, placeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return productService.findAllProductsByPlaceId(placeId, pageable);
    }

    private Page<Product> getAllProducts(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        var product = productService.findProductById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PRODUCT, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return productMapper.map(product);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid CreateProductDto dto) {
        var placeId = dto.getPlaceId();

        var place = placeService.findPlaceById(placeId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, placeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Product product = productMapper.map(dto, place);
        productService.saveProduct(product);
        return productMapper.map(product);
    }

    @PatchMapping("/{id}")
    public ProductDto updateProductById(@PathVariable long id, @RequestBody @Valid UpdateProductDto dto) {
        var product = productService.findProductById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PRODUCT, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Product updatedProduct = productMapper.map(dto, product);
        productService.saveProduct(updatedProduct);
        return productMapper.map(updatedProduct);
    }
}
