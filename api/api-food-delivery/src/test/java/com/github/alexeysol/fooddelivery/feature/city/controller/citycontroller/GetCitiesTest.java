package com.github.alexeysol.fooddelivery.feature.city.controller.citycontroller;

import com.github.alexeysol.fooddelivery.feature.city.model.entity.City;
import com.github.alexeysol.common.feature.city.model.dto.CityDto;
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

public class GetCitiesTest extends BaseCityControllerTest {
    public GetCitiesTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void productsExist_whenGetProducts_thenReturnsProductDtoList() {
        var products = List.of(new City(), new City());

        var productDtoList = List.of(CityDto.builder().build(), CityDto.builder().build());

        when(cityService.findAllCities()).thenReturn(products);
        when(cityMapper.map(Mockito.anyList())).thenReturn(productDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(productDtoList);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }
}
