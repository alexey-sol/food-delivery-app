package com.github.alexeysol.fooddelivery.feature.cart.controller.cartcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.cart.mapper.CartMapper;
import com.github.alexeysol.fooddelivery.feature.cart.service.CartService;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
import com.github.alexeysol.fooddelivery.feature.product.service.ProductService;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseCartControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected CartService cartService;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected PlaceService placeService;

    @MockBean
    protected CartMapper cartMapper;

    public BaseCartControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getUrl() {
        return String.format("/%s", ResourceConstant.CART);
    }

    protected String getUrl(long id) {
        return String.format("/%s/%d", ResourceConstant.CART, id);
    }
}
