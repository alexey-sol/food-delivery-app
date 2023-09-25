package com.github.alexeysol.fooddelivery.feature.place.controller.placecontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.city.service.CityService;
import com.github.alexeysol.fooddelivery.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BasePlaceControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MockMvc mockMvc;

    @MockBean
    protected CityService cityService;

    @MockBean
    protected PlaceService placeService;

    @MockBean
    protected PlaceMapper placeMapper;

    public BasePlaceControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getUrl() {
        return String.format("/%s", ResourceConstant.PLACE);
    }

    protected String getUrl(long id) {
        return String.format("/%s/%d", ResourceConstant.PLACE, id);
    }
}
