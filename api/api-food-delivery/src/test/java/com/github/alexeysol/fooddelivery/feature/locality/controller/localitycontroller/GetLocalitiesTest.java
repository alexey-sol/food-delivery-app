package com.github.alexeysol.fooddelivery.feature.locality.controller.localitycontroller;

import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
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

public class GetLocalitiesTest extends BaseLocalityControllerTest {
    public GetLocalitiesTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void productsExist_whenGetProducts_thenReturnsProductDtoList() {
        var products = List.of(new Locality(), new Locality());

        var productDtoList = List.of(LocalityDto.builder().build(), LocalityDto.builder().build());

        when(localityService.findAllLocalities()).thenReturn(products);
        when(localityMapper.map(Mockito.anyList())).thenReturn(productDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get(getLocalityUri()))
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
