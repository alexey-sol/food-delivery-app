package com.github.alexeysol.fooddelivery.feature.city.controller.citycontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.city.mapper.CityMapper;
import com.github.alexeysol.fooddelivery.feature.city.service.CityService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseCityControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected CityService cityService;

    @MockBean
    protected CityMapper cityMapper;

    public BaseCityControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getCityUri() {
        return String.format("/%s", ResourceConstant.CITY);
    }
}
