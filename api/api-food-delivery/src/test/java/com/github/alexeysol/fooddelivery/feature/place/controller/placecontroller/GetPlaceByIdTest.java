package com.github.alexeysol.fooddelivery.feature.place.controller.placecontroller;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
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


public class GetPlaceByIdTest extends BasePlaceControllerTest {
    public GetPlaceByIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenPlaceExists_whenGetPlaceById_thenReturnsPlaceDto() {
        var place = new Place();
        var placeDto = PlaceDto.builder().build();

        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.of(place));
        when(placeMapper.map(Mockito.any(Place.class))).thenReturn(placeDto);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl(1)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(placeDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenPlaceDoesntExist_whenGetPlaceById_thenThrowsResponseStatusException() {
        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl(0)))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
