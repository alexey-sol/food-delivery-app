package com.github.alexeysol.fooddelivery.feature.user.controller.usercontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.city.service.CityService;
import com.github.alexeysol.fooddelivery.feature.user.mapper.UserMapper;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseUserControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected CityService cityService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserMapper userMapper;

    public BaseUserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getUserUri() {
        return String.format("/%s", ResourceConstant.USER);
    }

    protected String getUserUri(long id) {
        return String.format("/%s/%d", ResourceConstant.USER, id);
    }
}
