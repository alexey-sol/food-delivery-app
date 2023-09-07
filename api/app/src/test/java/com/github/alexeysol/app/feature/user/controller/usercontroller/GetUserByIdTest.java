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
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class GetUserByIdTest extends BaseUserControllerTest {
    public GetUserByIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenUserExists_whenGetUserById_thenReturnsUserDto() {
        var user = new User();
        var userDto = UserDto.builder().build();

        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        when(userMapper.map(Mockito.any(User.class))).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl(1)))
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
    public void givenUserDoesntExist_whenGetUserById_thenThrowsResponseStatusException() {
        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl(0)))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
