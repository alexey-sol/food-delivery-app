package com.github.alexeysol.fooddelivery.feature.product.controller.productcontroller;

import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.UpdateProductDto;
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

public class UpdateProductByIdTest extends BaseProductControllerTest {
    public UpdateProductByIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenUpdateProductById_thenReturnsProductDto() {
        var updateProductDto = UpdateProductDto.builder().build();

        var product = new Product();
        var productDto = ProductDto.builder().build();

        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(productMapper.map(Mockito.any(UpdateProductDto.class), Mockito.any(Product.class))).thenReturn(product);
        when(productMapper.map(Mockito.any(Product.class))).thenReturn(productDto);

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(1), updateProductDto))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(productDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenAbsentProduct_whenUpdateProductById_thenThrowsResponseStatusException() {
        var updateProductDto = UpdateProductDto.builder().build();

        when(productService.findProductById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPatchRequest(getUrl(0), updateProductDto))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
