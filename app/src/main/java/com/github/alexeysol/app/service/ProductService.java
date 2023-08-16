package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.Product;
import com.github.alexeysol.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Set<Product> findAllProducts() {
        return new HashSet<>(productRepository.findAll());
    }

    public Set<Product> findAllProductsByStoreId(long storeId) {
        return new HashSet<>(productRepository.findAllByStoreId(storeId));
    }

    public Optional<Product> findProductById(long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
