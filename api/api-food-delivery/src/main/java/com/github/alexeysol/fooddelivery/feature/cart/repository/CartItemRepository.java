package com.github.alexeysol.fooddelivery.feature.cart.repository;

import com.github.alexeysol.fooddelivery.feature.cart.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(long cartId, long productId);
}
