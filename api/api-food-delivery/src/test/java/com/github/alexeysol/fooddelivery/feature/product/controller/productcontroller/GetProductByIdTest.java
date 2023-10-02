package com.github.alexeysol.fooddelivery.feature.product.controller.productcontroller;

import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
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

public class GetProductByIdTest extends BaseProductControllerTest {
    public GetProductByIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenProductExists_whenGetProductById_thenReturnsProductDto() {
        var product = new Product();
        var productDto = ProductDto.builder().build();

        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(productMapper.map(Mockito.any(Product.class))).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.get(getProductUri(1)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(productDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenProductDoesntExist_whenGetProductById_thenThrowsResponseStatusException() {
        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(getProductUri(0)))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
