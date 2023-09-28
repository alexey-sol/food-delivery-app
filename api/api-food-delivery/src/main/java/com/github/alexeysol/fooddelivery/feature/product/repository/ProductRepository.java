package com.github.alexeysol.fooddelivery.feature.product.repository;

import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN FETCH p.place")
    Page<Product> findAllByPlaceId(long placeId, Pageable pageable);

    @NonNull
    @Query("SELECT p FROM Product p JOIN FETCH p.place")
    Page<Product> findAll(@NonNull Pageable pageable);
}
