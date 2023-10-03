package com.github.alexeysol.fooddelivery.feature.user.controller.usercontroller;

import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
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
        .localityId(1L)
        .phone("70000000000")
        .password("password")
        .address(CreateAddressDto.builder()
            .addressLine("Address line")
            .build())
        .build();

    public CreateUserTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenCreateUser_thenReturnsUserDto() {
        var locality = new Locality();
        var user = new User();
        var userDto = UserDto.builder().build();

        when(localityService.findLocalityById(Mockito.anyLong())).thenReturn(Optional.of(locality));
        when(userMapper.map(Mockito.any(SignUpDto.class), Mockito.any(Locality.class))).thenReturn(user);
        when(userMapper.map(Mockito.any(User.class))).thenReturn(userDto);

        mockMvc.perform(TestUtil.mockPostRequest(getUserUri(), SIGN_UP_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(userDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenLocalityDoesntExist_whenCreateUser_thenThrowsResponseStatusException() {
        when(localityService.findLocalityById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPostRequest(getUserUri(), SIGN_UP_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
