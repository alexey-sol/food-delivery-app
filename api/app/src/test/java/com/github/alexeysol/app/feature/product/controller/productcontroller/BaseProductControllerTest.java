package com.github.alexeysol.app.feature.product.controller.productcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.app.feature.place.service.PlaceService;
import com.github.alexeysol.app.feature.product.mapper.ProductMapper;
import com.github.alexeysol.app.feature.product.service.ProductService;
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

    protected String getUrl() {
        return String.format("/%s", ResourceConstant.PRODUCT);
    }

    protected String getUrl(long id) {
        return String.format("/%s/%d", ResourceConstant.PRODUCT, id);
    }
}
