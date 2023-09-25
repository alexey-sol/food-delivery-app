package com.github.alexeysol.fooddelivery.feature.order.controller.ordercontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.cart.service.CartService;
import com.github.alexeysol.fooddelivery.feature.order.mapper.OrderMapper;
import com.github.alexeysol.fooddelivery.feature.order.service.OrderService;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseOrderControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected CartService cartService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected OrderMapper orderMapper;

    public BaseOrderControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getUrl() {
        return String.format("/%s", ResourceConstant.ORDER);
    }

    protected String getUrl(long id) {
        return String.format("/%s/%d", ResourceConstant.ORDER, id);
    }
}
