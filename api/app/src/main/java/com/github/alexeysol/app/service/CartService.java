package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.Cart;
import com.github.alexeysol.app.model.entity.CartItem;
import com.github.alexeysol.app.repository.CartItemRepository;
import com.github.alexeysol.app.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public List<Cart> findAllCartsByUserId(long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    public Optional<Cart> findCartByUserIdAndPlaceId(long userId, long placeId) {
        return cartRepository.findTopByUserIdAndPlaceId(userId, placeId);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCartById(long id) {
        cartRepository.deleteById(id);
    }

    public Optional<CartItem> findCartItemByCartIdAndProductId(long cartId, long productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItemById(long id) {
        cartItemRepository.deleteById(id);
    }
}
