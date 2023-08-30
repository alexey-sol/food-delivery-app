package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(long userId);

    Optional<Cart> findByStoreId(long storeId);
}
