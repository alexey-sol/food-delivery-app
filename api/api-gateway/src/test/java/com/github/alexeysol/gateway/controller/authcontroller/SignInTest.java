package com.github.alexeysol.gateway.controller.authcontroller;

import com.github.alexeysol.common.feature.user.model.dto.SignInDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.common.shared.util.TestUtil;
import com.github.alexeysol.gateway.constant.AuthConstant;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;

public class SignInTest extends BaseAuthControllerTest {
    private static final String PATH = "sign-in";
    private static final SignInDto SIGN_IN_DTO = SignInDto.builder()
        .phone("70000000000")
        .password("password")
        .build();

    public SignInTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidCredentials_whenSignIn_thenReturnsUserDtoWithAuthToken() {
        var userDto = new UserDto();

        when(userService.getUserByPhone(Mockito.anyString(), Mockito.anyString())).thenReturn(userDto);
        when(authService.getAuthCookie(Mockito.any(UserDto.class))).thenReturn(AUTH_TOKEN);

        mockMvc.perform(TestUtil.mockPostRequest(getAuthUri(PATH), SIGN_IN_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.cookie().exists(AuthConstant.AUTH_COOKIE_NAME))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(userDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenInvalidCredentials_whenSignIn_thenThrowsResponseStatusException() {
        when(userService.getUserByPhone(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(TestUtil.mockPostRequest(getAuthUri(PATH), SIGN_IN_DTO))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
            .andExpect(MockMvcResultMatchers.cookie().doesNotExist(AuthConstant.AUTH_COOKIE_NAME))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
