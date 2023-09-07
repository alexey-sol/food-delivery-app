package com.github.alexeysol.app.feature.user.controller.usercontroller;

import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class GetUserTest extends BaseUserControllerTest {
    private static final String PHONE = "70000000000";

    public GetUserTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenUserExists_whenGetUser_thenReturnsUserDto() {
        var user = new User();
        var userDto = UserDto.builder().build();

        when(userService.findUserByPhone(Mockito.anyString())).thenReturn(Optional.of(user));
        when(userMapper.map(Mockito.any(User.class))).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("phone", PHONE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(userDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenUserDoesntExist_whenGetUser_thenReturnsNull() {
        when(userService.findUserByPhone(Mockito.anyString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("phone", PHONE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var actual = result.getResponse().getContentAsString();
                Assertions.assertTrue(actual.isEmpty());
            });
    }

    @Test
    @SneakyThrows
    public void givenPasswordInvalid_whenGetUser_thenReturnsNull() {
        var user = new User();

        when(userService.findUserByPhone(Mockito.anyString())).thenReturn(Optional.of(user));
        when(userService.isValidPassword(Mockito.anyString(), Mockito.any(User.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("phone", PHONE)
                .param("password", "qwerty"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var actual = result.getResponse().getContentAsString();
                Assertions.assertTrue(actual.isEmpty());
            });
    }
}
