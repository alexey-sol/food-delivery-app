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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

//    private final CartMapper cartMapper = CartMapper.INSTANCE;
    private final CartMapper cartMapper;

    @GetMapping  // TODO query better: it's explicit
    public List<CartDto> getAllCartsByUserId(@RequestParam long userId) {
        var carts = cartService.findAllCartsByUserId(userId);
        return cartMapper.map(carts);
    }

    // TODO probably should be PUT since we adding/updating/deleting cart item entity
    @PatchMapping
    public CartDto saveCartItem(@RequestBody SaveCartItemDto dto) {
        // dto.productId
        // dto.quantity
        // dto.userId


        var product = productService.findProductById(dto.getProductId()).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PRODUCT_RESOURCE, dto.getProductId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var store = product.getStore();

        // TODO if no cart then create cart
        var optionalCart = cartService.findCartByUserIdAndStoreId(dto.getUserId(), store.getId());

//        var carts = cartService.findAllCartsByStoreIdAndUserId(store.getId(), dto.getUserId()); // there may be only 1 user's cart related to the store

        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            var user = userService.findUserById(dto.getUserId()).orElseThrow(() -> {
                var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER_RESOURCE, dto.getUserId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            var ccc = new HashSet<CartItem>();

            cart = Cart.builder()
//                .cartItems(new ArrayList<>())
                .cartItems(ccc)
                .store(store)
                .user(user)
                .build();
            // TODO need to save cart here?
//            cartService.saveCart(cart);

//            user.setCart(cart);
//            cartService.saveCart(cart);

            var updatedCarts = user.getCarts();
            updatedCarts.add(cart);
            user.setCarts(updatedCarts);
//            userService.saveUser(user);
        }

        var isAddingToCart = dto.getQuantity() > 0;

        var optionalCartItem = cartService.findCartItemByProductId(dto.getProductId());
        var cartItem = optionalCartItem.orElseGet(() -> CartItem.builder()
            .cart(cart)
            .product(product)
            .build());




//        var quantity = cartItem.getQuantity();
        var newPriceModulus = Math.abs(product.getPrice() * dto.getQuantity());

//        if (quantity > 0) { // TODO 0 to const
        if (dto.getQuantity() > 0) { // TODO 0 to const
            cart.addPrice(newPriceModulus);
        } else {
            if (cartItem.getQuantity() > 0) {
                cart.subtractPrice(newPriceModulus);
            }
        }

        var quantityModulus = Math.abs(dto.getQuantity());

        if (isAddingToCart) { // TODO check if cart is a right one
            cartItem.incrementQuantity(quantityModulus); // TODO add/subtract quantity
        } else {
            cartItem.decrementQuantity(quantityModulus);
        }





//        cartService.saveCart(cart);

        // TODO update cart.totalPrice as well

        // TODO cartItem.quantity == 0 ? delete
        if (cartItem.isIdle()) {
            cartService.deleteCartItemById(cartItem.getId());
        } else {
            cartService.saveCart(cart);
            cartService.saveCartItem(cartItem);


            var updatedCartItems = cart.getCartItems();
            updatedCartItems.add(cartItem);


        }


        if (cart.getCartItems().isEmpty()) { // TODO add isIdle method
            // TODO do i need delete cart if there's nothing left?
            cartService.deleteCartById(cart.getId());
            // TODO return null in this case? bit it will be inconvinietn in front
        }


        return cartMapper.map(cart);
    }
}
