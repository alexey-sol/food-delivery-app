package com.github.alexeysol.gateway.controller.authcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.gateway.constant.AuthConstant;
import com.github.alexeysol.gateway.service.AuthService;
import com.github.alexeysol.gateway.service.CityService;
import com.github.alexeysol.gateway.service.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseAuthControllerTest {
    private static final String AUTH = "auth";

    protected static final Cookie AUTH_TOKEN = new Cookie(AuthConstant.AUTH_COOKIE_NAME, "token");
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected CityService cityService;

    @MockBean
    protected UserService userService;

    public BaseAuthControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getAuthUri(String path) {
        return String.format("/%s/%s", AUTH, path);
    }
}
