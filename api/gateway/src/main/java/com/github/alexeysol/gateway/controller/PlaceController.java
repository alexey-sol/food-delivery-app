package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.model.dto.PlaceDto;
import com.github.alexeysol.common.model.dto.UpdatePlaceDto;
import com.github.alexeysol.gateway.service.PlaceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/place", produces = "application/json")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ServicePage<PlaceDto> getPlaces(HttpServletRequest request) {
        return placeService.getPlaces(request.getQueryString());
    }

    @GetMapping("/{id}")
    public PlaceDto getPlaceById(@PathVariable long id) {
        return placeService.getPlaceById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PlaceDto createPlace(@RequestBody CreatePlaceDto dto) {
        return placeService.createPlace(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public PlaceDto updatePlaceById(@PathVariable long id, @RequestBody UpdatePlaceDto dto) {
        return placeService.updatePlaceById(id, dto);
    }
}
