package com.github.alexeysol.gateway.controller.authcontroller;

import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
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

public class SignUpTest extends BaseAuthControllerTest {
    private static final String PATH = "sign-up";
    private static final SignUpDto SIGN_UP_DTO = SignUpDto.builder()
        .username("User")
        .cityId(1L)
        .phone("70000000000")
        .password("password")
        .address(CreateAddressDto.builder()
            .addressLine("Address line")
            .build())
        .build();

    public SignUpTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenUserDoesntExist_whenSignUp_thenReturnsUserDtoWithAuthToken() {
        var userDto = new UserDto();

        when(userService.getUserByPhone(Mockito.anyString())).thenReturn(null);
        when(userService.createUser(Mockito.any(SignUpDto.class))).thenReturn(userDto);
        when(authService.getAuthCookie(Mockito.any(UserDto.class))).thenReturn(AUTH_TOKEN);

        mockMvc.perform(TestUtil.mockPostRequest(getAuthUri(PATH), SIGN_UP_DTO))
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
    public void givenUserExists_whenSignUp_thenThrowsResponseStatusException() {
        var userDto = new UserDto();

        when(userService.getUserByPhone(Mockito.anyString())).thenReturn(userDto);

        mockMvc.perform(TestUtil.mockPostRequest(getAuthUri(PATH), SIGN_UP_DTO))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.cookie().doesNotExist(AuthConstant.AUTH_COOKIE_NAME))
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
