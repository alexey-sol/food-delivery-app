package com.github.alexeysol.app.feature.place.controller.placecontroller;

import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
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


public class UpdatePlaceByIdTest extends BasePlaceControllerTest {
    public UpdatePlaceByIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenUpdatePlaceById_thenReturnsPlaceDto() {
        var updatePlaceDto = UpdatePlaceDto.builder().build();

        var place = new Place();
        var placeDto = PlaceDto.builder().build();

        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.of(place));
        when(placeMapper.map(Mockito.any(UpdatePlaceDto.class), Mockito.any(Place.class))).thenReturn(place);
        when(placeMapper.map(Mockito.any(Place.class))).thenReturn(placeDto);

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(1), updatePlaceDto))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(placeDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenAbsentPlace_whenUpdatePlaceById_thenThrowsResponseStatusException() {
        var updatePlaceDto = UpdatePlaceDto.builder().build();

        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(0), updatePlaceDto))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
