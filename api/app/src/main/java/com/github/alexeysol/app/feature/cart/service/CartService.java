package com.github.alexeysol.app.feature.cart.service;

import com.github.alexeysol.app.feature.cart.mapper.CartItemMapper;
import com.github.alexeysol.app.feature.cart.mapper.CartMapper;
import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import com.github.alexeysol.app.feature.cart.model.entity.CartItem;
import com.github.alexeysol.app.feature.cart.repository.CartItemRepository;
import com.github.alexeysol.app.feature.cart.repository.CartRepository;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.product.model.entity.Product;
import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.cart.model.SaveCartItemOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    public List<Cart> findAllCartsByUserId(long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    public Optional<Cart> findCartByUserIdAndPlaceId(long userId, long placeId) {
        return cartRepository.findTopByUserIdAndPlaceId(userId, placeId);
    }

    public CartItem getOrCreateCartItem(User user, Place place, Product product) {
        var optionalCart = findCartByUserIdAndPlaceId(user.getId(), place.getId());

        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = cartMapper.map(user, place, new Cart());
            user.getCarts().add(cart);
        }

        var optionalCartItem = findCartItemByCartIdAndProductId(cart.getId(), product.getId());
        return optionalCartItem.orElseGet(() -> cartItemMapper.map(cart, product, new CartItem()));
    }

    public Cart saveCartItemInCart(CartItem cartItem, SaveCartItemOperation operation, int itemCount) {
        var cart = cartItem.getCart();
        var product = cartItem.getProduct();

        var isAddingToCart = operation.equals(SaveCartItemOperation.ADD);
        var isRemovingFromCart = operation.equals(SaveCartItemOperation.REMOVE) && cartItem.getQuantity() > 0;
        var totalPrice = product.getPrice() * itemCount;

        if (isAddingToCart) {
            cart.incrementTotalPrice(totalPrice);
            cartItem.incrementQuantity(itemCount);
        } else if (isRemovingFromCart) {
            cart.decrementTotalPrice(totalPrice);
            cartItem.decrementQuantity(itemCount);
        }

        if (cartItem.isIdle()) {
            deleteCartItemById(cartItem.getId());
        } else {
            saveCart(cart);
            saveCartItem(cartItem);
            cart.getCartItems().add(cartItem);
        }

        if (cart.isIdle()) {
            deleteCartById(cart.getId());
        }

        return cart;
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
