package com.github.alexeysol.fooddelivery.feature.place.controller.placecontroller;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

public class GetPlacesTest extends BasePlaceControllerTest {
    private final static PageRequest pageableStub = PageRequest.of(0, 20);

    public GetPlacesTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void placesExist_whenGetPlaces_thenReturnsPlaceDtoPage() {
        var places = List.of(Place.builder().id(1).build(), Place.builder().id(2).build());
        var placePage = new PageImpl<>(places, pageableStub, places.size());

        var placeDtoList = List.of(PlaceDto.builder().id(1L).build(), PlaceDto.builder().id(2L).build());
        var placeDtoPage = new PageImpl<>(placeDtoList, pageableStub, placeDtoList.size());

        when(placeService.findAllPlaces(Mockito.any(Pageable.class))).thenReturn(placePage);

        mockMvc.perform(MockMvcRequestBuilders.get(getPlaceUri()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(placeDtoPage);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void placesDontExist_whenGetPlaces_thenReturnsEmptyPage() {
        var emptyPage = new PageImpl<Place>(List.of(), pageableStub, 0);

        var emptyDtoPage = new PageImpl<>(List.of(), pageableStub, 0);

        when(placeService.findAllPlaces(Mockito.any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getPlaceUri()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(emptyDtoPage);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }
}
