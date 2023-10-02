package com.github.alexeysol.gateway.controller.authcontroller;

import com.github.alexeysol.common.shared.util.TestUtil;
import com.github.alexeysol.gateway.constant.AuthConstant;
import jakarta.servlet.http.Cookie;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

public class SignOutTest extends BaseAuthControllerTest {
    private static final String PATH = "sign-out";
    private static final int EXPIRED_COOKIE_MAX_AGE_SECONDS = 0;

    public SignOutTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenAuthToken_whenSignOut_thenReturnsTrueAndExpiredAuthToken() {
        var expiredAuthToken = new Cookie(AuthConstant.AUTH_COOKIE_NAME, null);
        expiredAuthToken.setMaxAge(EXPIRED_COOKIE_MAX_AGE_SECONDS);

        when(authService.getExpiredAuthCookie()).thenReturn(expiredAuthToken);

        mockMvc.perform(TestUtil.mockPostRequest(getAuthUri(PATH))
                .cookie(AUTH_TOKEN))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.cookie().exists(AuthConstant.AUTH_COOKIE_NAME))
            .andExpect(MockMvcResultMatchers.cookie().maxAge(AuthConstant.AUTH_COOKIE_NAME, EXPIRED_COOKIE_MAX_AGE_SECONDS))
            .andExpect(result -> {
                var actual = result.getResponse().getContentAsString();
                Assertions.assertTrue(Boolean.parseBoolean(actual));
            });
    }

    @Test
    @SneakyThrows
    public void givenNoAuthToken_whenSignOut_thenReturnsFalse() {
        mockMvc.perform(TestUtil.mockPostRequest(getAuthUri(PATH)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.cookie().doesNotExist(AuthConstant.AUTH_COOKIE_NAME))
            .andExpect(result -> {
                var actual = result.getResponse().getContentAsString();
                Assertions.assertFalse(Boolean.parseBoolean(actual));
            });
    }
}
