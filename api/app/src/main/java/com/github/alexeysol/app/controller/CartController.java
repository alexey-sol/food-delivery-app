package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.CartMapper;
import com.github.alexeysol.app.model.entity.Cart;
import com.github.alexeysol.app.model.entity.CartItem;
import com.github.alexeysol.app.service.CartService;
import com.github.alexeysol.app.service.ProductService;
import com.github.alexeysol.app.service.UserService;
import com.github.alexeysol.common.model.dto.CartDto;
import com.github.alexeysol.common.model.dto.SaveCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/cart", produces = "application/json")
@RequiredArgsConstructor
public class CartController {
    private final static String CART_RESOURCE = "Cart";
    private final static String PRODUCT_RESOURCE = "Product";
    private final static String USER_RESOURCE = "User";

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    private final CartMapper cartMapper = CartMapper.INSTANCE;

    @GetMapping
    public CartDto getCart(@RequestParam long userId) {
        var cart = cartService.findCartByUserId(userId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND, CART_RESOURCE);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return cartMapper.map(cart);
    }

    // TODO probably should be PUT since we adding/updating/deleting cart item entity
    @PatchMapping
    public CartDto saveCartItem(@RequestBody SaveCartItemDto dto) {
        var product = productService.findProductById(dto.getProductId()).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PRODUCT_RESOURCE, dto.getProductId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        // TODO if no cart then create cart
        var optionalCart = cartService.findCartByUserId(dto.getUserId());
        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            var user = userService.findUserById(dto.getUserId()).orElseThrow(() -> {
                var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER_RESOURCE, dto.getUserId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            cart = Cart.builder()
                .user(user)
                .build();
            // TODO need to save cart here?
//            cartService.saveCart(cart);
            user.setCart(cart);
            userService.saveUser(user);
        }

        var isAddingToCart = dto.getQuantity() > 0;

        var optionalCartItem = cartService.findCartItemByProductId(dto.getProductId());
        var cartItem = optionalCartItem.orElseGet(() -> CartItem.builder()
            .cart(cart)
            .product(product)
            .build());

        var quantityModulus = Math.abs(dto.getQuantity());

        if (isAddingToCart) {
            cartItem.incrementQuantity(quantityModulus); // TODO add/subtract quantity
        } else {
            cartItem.decrementQuantity(quantityModulus);
        }


//        var quantity = cartItem.getQuantity();
        var newPriceModulus = Math.abs(product.getPrice() * dto.getQuantity());

//        if (quantity > 0) { // TODO 0 to const
        if (dto.getQuantity() > 0) { // TODO 0 to const
            cart.addPrice(newPriceModulus);
        } else {
            cart.subtractPrice(newPriceModulus);
        }

        cartService.saveCart(cart);

        // TODO update cart.totalPrice as well

        // TODO cartItem.quantity == 0 ? delete
        if (cartItem.isIdle()) {
            cartService.deleteCartItemById(cartItem.getId());
        } else {
            cartService.saveCartItem(cartItem);
        }





        return cartMapper.map(cart);
    }
}
