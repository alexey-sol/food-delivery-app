package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.Product;
import com.github.alexeysol.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO don't need it seems
@Service
@RequiredArgsConstructor
public class ProductServiceTestttImpl implements ProductServiceTesttt {
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

    // TODO test for mapping:
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }
}
