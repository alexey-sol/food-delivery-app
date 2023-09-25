package com.github.alexeysol.fooddelivery.feature.cart.controller.cartcontroller;

import com.github.alexeysol.fooddelivery.feature.cart.model.entity.Cart;
import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

public class GetAllCartsByUserIdTest extends BaseCartControllerTest {
    public GetAllCartsByUserIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void cartsExist_whenGetAllCartsByUserId_thenReturnsCartDtoList() {
        var carts = List.of(new Cart(), new Cart());

        var cartDtoList = List.of(CartDto.builder().build(), CartDto.builder().build());

        when(cartService.findAllCartsByUserId(Mockito.anyLong())).thenReturn(carts);
        when(cartMapper.map(Mockito.anyList())).thenReturn(cartDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("userId", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(cartDtoList);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }
}
