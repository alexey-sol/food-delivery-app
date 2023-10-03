package com.github.alexeysol.fooddelivery.feature.locality.controller.localitycontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.locality.mapper.LocalityMapper;
import com.github.alexeysol.fooddelivery.feature.locality.service.LocalityService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseLocalityControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected LocalityService localityService;

    @MockBean
    protected LocalityMapper localityMapper;

    public BaseLocalityControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getLocalityUri() {
        return String.format("/%s", ResourceConstant.LOCALITY);
    }
}
