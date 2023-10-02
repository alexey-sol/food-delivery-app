package com.github.alexeysol.fooddelivery.feature.product.controller.productcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
import com.github.alexeysol.fooddelivery.feature.product.mapper.ProductMapper;
import com.github.alexeysol.fooddelivery.feature.product.service.ProductService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseProductControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected PlaceService placeService;

    @MockBean
    protected ProductMapper productMapper;

    public BaseProductControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getProductUri() {
        return String.format("/%s", ResourceConstant.PRODUCT);
    }

    protected String getProductUri(long id) {
        return String.format("/%s/%d", ResourceConstant.PRODUCT, id);
    }

    protected String getPlaceProductUri(long userId) {
        return String.format("/%s/%d/%s", ResourceConstant.PLACE, userId, ResourceConstant.PRODUCT);
    }
}
