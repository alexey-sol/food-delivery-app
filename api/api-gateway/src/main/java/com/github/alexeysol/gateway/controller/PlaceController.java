package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import com.github.alexeysol.common.shared.model.ServicePage;
import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
import com.github.alexeysol.gateway.service.PlaceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/place")
    public ServicePage<PlacePreviewDto> getPlaces(HttpServletRequest request) {
        return placeService.getPlaces(request.getQueryString());
    }

    @GetMapping("/place/{id}")
    public PlaceDto getPlaceById(@PathVariable long id) {
        return placeService.getPlaceById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/place/{id}")
    public PlaceDto updatePlaceById(@PathVariable long id, @RequestBody UpdatePlaceDto dto) {
        return placeService.updatePlaceById(id, dto);
    }

    @GetMapping("/city/{cityId}/place")
    public ServicePage<PlacePreviewDto> getPlacesByCityId(@PathVariable long cityId, HttpServletRequest request) {
        return placeService.getPlacesByCityId(cityId, request.getQueryString());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/city/{cityId}/place")
    public PlaceDto createPlace(@PathVariable long cityId, @RequestBody CreatePlaceDto dto) {
        return placeService.createPlace(cityId, dto);
    }
}
