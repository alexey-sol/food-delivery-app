package com.github.alexeysol.fooddelivery.feature.product.repository;

import com.github.alexeysol.fooddelivery.feature.product.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
