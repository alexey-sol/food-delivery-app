package com.github.alexeysol.app.feature.product.controller.productcontroller;

import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.product.model.entity.Product;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class GetProductsTest extends BaseProductControllerTest {
    private final static PageRequest pageableStub = PageRequest.of(0, 10);

    public GetProductsTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void productsExist_whenGetProducts_thenReturnsProductDtoPage() {
        var products = List.of(new Product(), new Product());
        var productPage = new PageImpl<>(products, pageableStub, products.size());

        var productDtoList = List.of(ProductDto.builder().build(), ProductDto.builder().build());
        var productDtoPage = new PageImpl<>(productDtoList, pageableStub, productDtoList.size());

        when(productService.findAllProducts(Mockito.any(PageRequest.class))).thenReturn(productPage);
        when(productMapper.map(Mockito.any(), Mockito.any(Pageable.class))).thenReturn(productDtoPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(productDtoPage);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void productsExist_whenGetProductsByPlaceId_thenReturnsProductDtoPage() {
        var products = List.of(new Product(), new Product());
        var productPage = new PageImpl<>(products, pageableStub, products.size());

        var productDtoList = List.of(ProductDto.builder().build(), ProductDto.builder().build());
        var productDtoPage = new PageImpl<>(productDtoList, pageableStub, productDtoList.size());

        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.of(new Place()));
        when(productService.findAllProductsByPlaceId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(productPage);
        when(productMapper.map(Mockito.any(), Mockito.any(Pageable.class))).thenReturn(productDtoPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("placeId", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(productDtoPage);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void placeDoesntExist_whenGetProductsByPlaceId_thenThrowsResponseStatusException() {
        when(placeService.findPlaceById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("placeId", "0"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
