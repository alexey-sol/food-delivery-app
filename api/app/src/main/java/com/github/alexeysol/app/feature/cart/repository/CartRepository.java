package com.github.alexeysol.app.feature.cart.repository;

import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(long userId);

    List<Cart> findAllByPlaceIdAndUserId(long placeId, long userId);

    Optional<Cart> findByPlaceId(long placeId);

    Optional<Cart> findTopByUserIdAndPlaceId(long userId, long placeId);
}
