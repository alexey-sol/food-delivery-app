package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.common.shared.model.ServicePage;
import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final GatewayConfig config;

    public ServicePage<PlaceDto> getPlaces(String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.PLACE)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<PlaceDto>>() {})
            .block();
    }

    public PlaceDto getPlaceById(long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.PLACE, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    public PlaceDto createPlace(CreatePlaceDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(ResourceConstant.PLACE).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    public PlaceDto updatePlaceById(long id, UpdatePlaceDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(ResourceConstant.PLACE, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }
}
