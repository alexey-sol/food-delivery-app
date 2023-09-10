package com.github.alexeysol.app.feature.cart.controller;

import com.github.alexeysol.app.feature.cart.mapper.CartMapper;
import com.github.alexeysol.app.feature.cart.service.CartService;
import com.github.alexeysol.app.feature.place.service.PlaceService;
import com.github.alexeysol.app.feature.product.service.ProductService;
import com.github.alexeysol.app.feature.user.service.UserService;
import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import com.github.alexeysol.common.feature.cart.model.dto.SaveCartItemDto;
import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/cart", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class CartController {
    private final static String PLACE = "Place";
    private final static String PRODUCT = "Product";
    private final static String USER = "User";

    private final CartService cartService;
    private final PlaceService placeService;
    private final ProductService productService;
    private final UserService userService;
    private final CartMapper cartMapper;

    @GetMapping
    public List<CartDto> getAllCartsByUserId(@RequestParam long userId) {
        var carts = cartService.findAllCartsByUserId(userId);
        return cartMapper.map(carts);
    }

    @PatchMapping
    public CartDto saveCartItem(@RequestBody @Valid SaveCartItemDto dto) {
        var user = userService.findUserById(dto.getUserId()).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER, dto.getUserId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var place = placeService.findPlaceById(dto.getPlaceId()).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, dto.getPlaceId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var product = productService.findProductById(dto.getProductId()).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PRODUCT, dto.getProductId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var cartItem = cartService.getOrCreateCartItem(user, place, product);
        var cart = cartService.saveCartItemInCart(cartItem, dto.getOperation(), dto.getCount());
        return cartMapper.map(cart);
    }
}
