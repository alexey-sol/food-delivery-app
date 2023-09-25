package com.github.alexeysol.fooddelivery.feature.user.controller.usercontroller;

import com.github.alexeysol.fooddelivery.feature.city.model.entity.City;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
import com.github.alexeysol.common.shared.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class CreateUserTest extends BaseUserControllerTest {
    private static final SignUpDto SIGN_UP_DTO = SignUpDto.builder()
        .username("User")
        .phone("70000000000")
        .password("password")
        .address(CreateAddressDto.builder()
            .cityId(1L)
            .addressLine("Address line")
            .build())
        .build();

    public CreateUserTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenCreateUser_thenReturnsUserDto() {
        var city = new City();
        var user = new User();
        var userDto = UserDto.builder().build();

        when(cityService.findCityById(Mockito.anyLong())).thenReturn(Optional.of(city));
        when(userMapper.map(Mockito.any(SignUpDto.class), Mockito.any(City.class))).thenReturn(user);
        when(userMapper.map(Mockito.any(User.class))).thenReturn(userDto);

        mockMvc.perform(TestUtil.mockPostRequest(getUrl(), SIGN_UP_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(userDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenCityDoesntExist_whenCreateUser_thenThrowsResponseStatusException() {
        when(cityService.findCityById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPostRequest(getUrl(), SIGN_UP_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
