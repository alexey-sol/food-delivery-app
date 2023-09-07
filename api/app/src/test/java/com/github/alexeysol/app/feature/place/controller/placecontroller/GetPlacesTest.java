package com.github.alexeysol.app.feature.place.controller.placecontroller;

import com.github.alexeysol.app.feature.place.model.entity.Place;
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
    private final static PageRequest pageableStub = PageRequest.of(0, 10);

    public GetPlacesTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void placesExist_whenGetPlaces_thenReturnsPlaceDtoPage() {
        var places = List.of(new Place(), new Place());
        var placePage = new PageImpl<>(places, pageableStub, places.size());

        var placeDtoList = List.of(PlaceDto.builder().build(), PlaceDto.builder().build());
        var placeDtoPage = new PageImpl<>(placeDtoList, pageableStub, placeDtoList.size());

        when(placeService.findAllPlaces(Mockito.any(Pageable.class))).thenReturn(placePage);
        when(placeMapper.map(Mockito.any(), Mockito.any(PageRequest.class))).thenReturn(placeDtoPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl()))
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
    public void placesExist_whenGetPlacesByCityId_thenReturnsPlaceDtoPage() {
        var places = List.of(new Place(), new Place());
        var placePage = new PageImpl<>(places, pageableStub, places.size());

        var placeDtoList = List.of(PlaceDto.builder().build(), PlaceDto.builder().build());
        var placeDtoPage = new PageImpl<>(placeDtoList, pageableStub, placeDtoList.size());

        when(placeService.findAllPlacesByCityId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(placePage);
        when(placeMapper.map(Mockito.any(), Mockito.any(PageRequest.class))).thenReturn(placeDtoPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("cityId", "1"))
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

        var emptyDtoList = List.of(PlaceDto.builder().build(), PlaceDto.builder().build());
        var emptyDtoPage = new PageImpl<>(emptyDtoList, pageableStub, emptyDtoList.size());

        when(placeService.findAllPlaces(Mockito.any(Pageable.class))).thenReturn(emptyPage);
        when(placeMapper.map(Mockito.any(), Mockito.any(PageRequest.class))).thenReturn(emptyDtoPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(emptyDtoPage);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }
}
