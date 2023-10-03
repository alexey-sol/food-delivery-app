package com.github.alexeysol.fooddelivery.feature.place.controller.placecontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeysol.fooddelivery.feature.locality.service.LocalityService;
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
    protected LocalityService localityService;

    @MockBean
    protected PlaceService placeService;

    @MockBean
    protected PlaceMapper placeMapper;

    public BasePlaceControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected String getPlaceUri() {
        return String.format("/%s", ResourceConstant.PLACE);
    }

    protected String getPlaceUri(long id) {
        return String.format("/%s/%d", ResourceConstant.PLACE, id);
    }

    protected String getLocalityPlaceUri(long userId) {
        return String.format("/%s/%d/%s", ResourceConstant.LOCALITY, userId, ResourceConstant.PLACE);
    }
}
