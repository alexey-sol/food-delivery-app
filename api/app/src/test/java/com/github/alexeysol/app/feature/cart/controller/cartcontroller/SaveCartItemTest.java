package com.github.alexeysol.app.feature.cart.controller.cartcontroller;

import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import com.github.alexeysol.app.feature.cart.model.entity.CartItem;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.product.model.entity.Product;
import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import com.github.alexeysol.common.feature.cart.model.dto.SaveCartItemDto;
import com.github.alexeysol.common.shared.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class SaveCartItemTest extends BaseCartControllerTest {
    private static final SaveCartItemDto SAVE_CART_ITEM_DTO = SaveCartItemDto.builder()
        .productId(1L)
        .userId(1L)
        .quantity(10)
        .build();

    public SaveCartItemTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenSaveCartItem_thenReturnsCartDto() {
        var place = Place.builder()
            .id(1L)
            .build();
        var product = Product.builder()
            .place(place)
            .build();
        var user = new User();
        var cart = new Cart();
        var cartItem = new CartItem();
        var cartDto = CartDto.builder().build();

        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(cartService.findCartByUserIdAndPlaceId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(cart));
        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        when(cartService.findCartItemByCartIdAndProductId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(cartItem));
        when(cartMapper.map(Mockito.any(Cart.class))).thenReturn(cartDto);

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(), SAVE_CART_ITEM_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(cartDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenUserDoesntExist_whenSaveCartItem_thenThrowsResponseStatusException() {
        var place = Place.builder()
            .id(1L)
            .build();
        var product = Product.builder()
            .place(place)
            .build();

        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(cartService.findCartByUserIdAndPlaceId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());
        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(), SAVE_CART_ITEM_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
