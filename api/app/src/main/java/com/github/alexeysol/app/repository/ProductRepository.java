package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaRepository<Product, Long> {
    Page<Product> findAllByStoreId(long storeId, Pageable pageable);
}
