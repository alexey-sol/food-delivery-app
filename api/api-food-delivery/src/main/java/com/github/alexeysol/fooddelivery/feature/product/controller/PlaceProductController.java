package com.github.alexeysol.fooddelivery.feature.product.controller;

import com.github.alexeysol.common.feature.product.model.dto.CreateProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductPreviewDto;
import com.github.alexeysol.common.feature.place.exception.PlaceNotFoundException;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
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
@RequestMapping(value = "/place/{placeId}/product", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class PlaceProductController {
    private final ProductService productService;
    private final PlaceService placeService;
    private final ProductMapper productMapper;
    private final ProductPreviewMapper productPreviewMapper;

    @GetMapping
    public Page<ProductPreviewDto> getProductsByPlaceId(
        @PathVariable("placeId") long placeId,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        placeService.findPlaceById(placeId).orElseThrow(() -> {
            throw new PlaceNotFoundException();
        });

        var pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.findAllProductsByPlaceId(placeId, pageable);
        return productPreviewMapper.map(productPage, pageable);
    }

    @PostMapping
    public ProductDto createProduct(
        @PathVariable("placeId") long placeId,
        @RequestBody @Valid CreateProductDto dto
    ) {
        var place = placeService.findPlaceById(placeId).orElseThrow(() -> {
            throw new PlaceNotFoundException();
        });

        Product product = productMapper.map(dto, place);
        productService.saveProduct(product);
        return productMapper.map(product);
    }
}
