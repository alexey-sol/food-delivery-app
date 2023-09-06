package com.github.alexeysol.app.feature.product.repository;

import com.github.alexeysol.app.feature.product.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
