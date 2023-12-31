package com.github.alexeysol.fooddelivery.feature.product.controller.productcontroller;

import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import com.jayway.jsonpath.JsonPath;
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

public class GetProductsTest extends BaseProductControllerTest {
    private final static PageRequest pageableStub = PageRequest.of(0, 20);

    public GetProductsTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void productsExist_whenGetProducts_thenReturnsProductDtoPage() {
        var products = List.of(Product.builder().id(1).build(), Product.builder().id(2).build());
        var productPage = new PageImpl<>(products, pageableStub, products.size());

        var productDtoList = List.of(ProductDto.builder().id(1L).build(), ProductDto.builder().id(2L).build());
        var productDtoPage = new PageImpl<>(productDtoList, pageableStub, productDtoList.size());

        when(productService.findAllProducts(Mockito.any(PageRequest.class))).thenReturn(productPage);
        when(productMapper.map(Mockito.any(), Mockito.any(Pageable.class))).thenReturn(productDtoPage);

        mockMvc.perform(MockMvcRequestBuilders.get(getProductUri()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
            .andExpect(result -> {
                var actualContent = result.getResponse().getContentAsString();
                int expectedContentSize = productDtoPage.getContent().size();
                int actualContentSize = JsonPath.read(actualContent, "$.content.length()");

                Assertions.assertEquals(expectedContentSize, actualContentSize);
            });
    }
}
