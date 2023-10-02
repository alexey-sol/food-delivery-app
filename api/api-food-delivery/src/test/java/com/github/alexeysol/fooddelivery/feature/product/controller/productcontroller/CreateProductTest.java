package com.github.alexeysol.fooddelivery.feature.product.controller.productcontroller;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.github.alexeysol.common.feature.product.model.dto.CreateProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.shared.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class CreateProductTest extends BaseProductControllerTest {
    private static final CreateProductDto CREATE_PRODUCT_DTO = CreateProductDto.builder()
        .name("Product")
        .price(100L)
        .build();

    public CreateProductTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenCreateProduct_thenReturnsProductDto() {
        var place = new Place();
        var product = new Product();
        var productDto = ProductDto.builder().build();

        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.of(place));
        when(productMapper.map(Mockito.any(CreateProductDto.class), Mockito.any(Place.class))).thenReturn(product);
        when(productMapper.map(Mockito.any(Product.class))).thenReturn(productDto);

        mockMvc.perform(TestUtil.mockPostRequest(getPlaceProductUri(1), CREATE_PRODUCT_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(productDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenInvalidDto_whenCreateProduct_thenThrowsMethodArgumentNotValidException() {
        var createProductDto = CreateProductDto.builder().build();

        mockMvc.perform(TestUtil.mockPostRequest(getPlaceProductUri(1), createProductDto))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @SneakyThrows
    public void givenPlaceDoesntExist_whenCreateProduct_thenThrowsResponseStatusException() {
        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPostRequest(getPlaceProductUri(1), CREATE_PRODUCT_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
