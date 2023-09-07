package com.github.alexeysol.app.feature.place.controller.placecontroller;

import com.github.alexeysol.app.feature.city.model.entity.City;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
import com.github.alexeysol.common.shared.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

import static org.mockito.Mockito.when;


public class CreatePlaceTest extends BasePlaceControllerTest {
    public CreatePlaceTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void providedValidDto_whenCreatePlace_thenReturnsPlaceDto() {
        var createPlaceAddressDto = CreateAddressDto.builder()
            .cityId(1L)
            .addressLine("Address line")
            .build();
        var createPlaceDto = CreatePlaceDto.builder()
            .name("Place")
            .address(createPlaceAddressDto)
            .build();

        var city = new City();
        var place = new Place();
        var placeDto = PlaceDto.builder().build();

        when(cityService.findCityById(Mockito.anyLong())).thenReturn(Optional.of(city));
        when(placeMapper.map(Mockito.any(CreatePlaceDto.class), Mockito.any(City.class))).thenReturn(place);
        when(placeService.savePlace(Mockito.any(Place.class))).thenReturn(place);
        when(placeMapper.map(Mockito.any(Place.class))).thenReturn(placeDto);

        mockMvc.perform(TestUtil.mockPostRequest(getUrl(), createPlaceDto))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(placeDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void providedInvalidDto_whenCreatePlace_thenThrowsMethodArgumentNotValidException() {
        var createPlaceDto = CreatePlaceDto.builder().build();

        mockMvc.perform(TestUtil.mockPostRequest(getUrl(), createPlaceDto))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> {
                Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
            });
    }
}
