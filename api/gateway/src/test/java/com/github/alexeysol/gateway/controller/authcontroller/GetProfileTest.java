package com.github.alexeysol.gateway.controller.authcontroller;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.common.shared.model.dto.InitDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

public class GetProfileTest extends BaseAuthControllerTest {
    private static final String PATH = "profile";
    private static final List<CityDto> CITY_DTO_LIST = List.of(new CityDto(), new CityDto());

    public GetProfileTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenAuthToken_whenGetProfile_thenReturnsInitDto() {
        var userDto = new UserDto();
        var initDto = InitDto.builder()
            .cities(CITY_DTO_LIST)
            .profile(userDto)
            .build();

        when(authService.getProfileIfAvailable(Mockito.anyString())).thenReturn(userDto);
        when(cityService.getAllCities()).thenReturn(CITY_DTO_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl(PATH))
                .cookie(AUTH_TOKEN))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(initDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenNoAuthToken_whenGetProfile_thenReturnsInitDtoWithNoProfile() {
        var cityDtoList = List.of(new CityDto(), new CityDto());
        var userDto = new UserDto();
        var initDto = InitDto.builder()
            .cities(cityDtoList)
            .build();

        when(authService.getProfileIfAvailable(Mockito.anyString())).thenReturn(userDto);
        when(cityService.getAllCities()).thenReturn(CITY_DTO_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl(PATH)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(initDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }
}
