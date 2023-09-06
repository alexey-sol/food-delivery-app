package com.github.alexeysol.app.feature.product.repository;

import com.github.alexeysol.app.feature.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaRepository<Product, Long> {
    Page<Product> findAllByPlaceId(long placeId, Pageable pageable);
}
