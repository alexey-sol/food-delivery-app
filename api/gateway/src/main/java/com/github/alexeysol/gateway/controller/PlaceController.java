package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.gateway.config.GatewayConfig;
import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.model.dto.PlaceDto;
import com.github.alexeysol.common.model.dto.UpdatePlaceDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
@RequestMapping(value = "/place", produces = "application/json")
@RequiredArgsConstructor
public class PlaceController {
    private final static String PLACE_RESOURCE = "place";

    private final GatewayConfig config;

    @GetMapping
    public ServicePage<PlaceDto> getPlaces(HttpServletRequest request) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(PLACE_RESOURCE)
                .query(request.getQueryString())
                .build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ServicePage<PlaceDto>>() {})
            .block();
    }

    @GetMapping("/{id}")
    public PlaceDto getPlaceById(@PathVariable long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(PLACE_RESOURCE, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    // TODO use enum somehow?
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PlaceDto createPlace(@RequestBody CreatePlaceDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(PLACE_RESOURCE).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public PlaceDto updatePlaceById(@RequestBody UpdatePlaceDto dto, @PathVariable long id) {
        return config.appWebClient()
            .patch()
            .uri(builder -> builder.pathSegment(PLACE_RESOURCE, String.valueOf(id)).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(PlaceDto.class)
            .block();
    }
}
