package com.github.alexeysol.fooddelivery.feature.product.service;

import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.github.alexeysol.fooddelivery.feature.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findAllProductsByPlaceId(long placeId, Pageable pageable) {
        return productRepository.findAllByPlaceId(placeId, pageable);
    }

    public Optional<Product> findProductById(long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findProductByIdIfExists(long id) {
        return productRepository.findById(id).orElse(null);
    }
}
