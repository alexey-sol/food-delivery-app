package com.github.alexeysol.fooddelivery.feature.cart.repository;

import com.github.alexeysol.fooddelivery.feature.cart.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
   @Query("SELECT c FROM Cart c " +
        "JOIN FETCH c.place " +
        "JOIN FETCH c.place.address " +
        "JOIN FETCH c.cartItems AS cartItem " +
        "JOIN FETCH cartItem.product")
    List<Cart> findAllByUserId(long userId);

    Optional<Cart> findTopByUserIdAndPlaceId(long userId, long placeId);
}
