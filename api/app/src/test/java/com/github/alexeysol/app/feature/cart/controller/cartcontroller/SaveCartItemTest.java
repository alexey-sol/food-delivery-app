package com.github.alexeysol.app.feature.cart.controller.cartcontroller;

import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import com.github.alexeysol.app.feature.cart.model.entity.CartItem;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.product.model.entity.Product;
import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.cart.model.SaveCartItemOperation;
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
        .placeId(1L)
        .productId(1L)
        .userId(1L)
        .count(1)
        .operation(SaveCartItemOperation.ADD)
        .build();

    public SaveCartItemTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenSaveCartItem_thenReturnsCartDto() {
        var user = new User();
        var place = new Place();
        var product = new Product();
        var cartItem = new CartItem();
        var cart = new Cart();
        var cartDto = CartDto.builder().build();

        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.of(place));
        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(cartService.getOrCreateCartItem(Mockito.any(User.class), Mockito.any(Place.class),
            Mockito.any(Product.class))).thenReturn(cartItem);
        when(cartService.saveCartItemInCart(Mockito.any(CartItem.class), Mockito.any(),
            Mockito.anyInt())).thenReturn(cart);
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
        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(), SAVE_CART_ITEM_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
