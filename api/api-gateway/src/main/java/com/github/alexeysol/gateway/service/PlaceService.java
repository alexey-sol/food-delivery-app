package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
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
import org.springframework.web.util.UriBuilder;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final GatewayConfig config;

    public ServicePage<PlacePreviewDto> getPlaces(String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> getPlaceUri(builder)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<PlacePreviewDto>>() {})
            .block();
    }

    public PlaceDto getPlaceById(long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> getPlaceUri(builder, id).build())
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    public PlaceDto updatePlaceById(long id, UpdatePlaceDto dto) {
        return config.appWebClient()
            .patch()
            .uri(builder -> getPlaceUri(builder, id).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    private UriBuilder getPlaceUri(UriBuilder builder) {
        return builder.pathSegment(ResourceConstant.PLACE);
    }

    private UriBuilder getPlaceUri(UriBuilder builder, long id) {
        return builder.pathSegment(ResourceConstant.PLACE, String.valueOf(id));
    }

    public ServicePage<PlacePreviewDto> getPlacesByCityId(long cityId, String query) {
        return config.appWebClient()
            .get()
            .uri(builder -> getCityPlaceUri(builder, cityId)
                .query(query)
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<PlacePreviewDto>>() {})
            .block();
    }

    public PlaceDto createPlace(long cityId, CreatePlaceDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> getCityPlaceUri(builder, cityId).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    private UriBuilder getCityPlaceUri(UriBuilder builder, long cityId) {
        return builder.pathSegment(ResourceConstant.CITY, String.valueOf(cityId), ResourceConstant.PLACE);
    }
}
