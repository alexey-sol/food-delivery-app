package com.github.alexeysol.fooddelivery.feature.place.controller;

import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import com.github.alexeysol.common.feature.city.exception.CityNotFoundException;
import com.github.alexeysol.fooddelivery.feature.city.service.CityService;
import com.github.alexeysol.fooddelivery.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.fooddelivery.feature.place.mapper.PlacePreviewMapper;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/city/{cityId}/place", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class CityPlaceController {
    private final CityService cityService;
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final PlacePreviewMapper placePreviewMapper;

    @GetMapping
    public Page<PlacePreviewDto> getPlacesByCityId(
        @PathVariable("cityId") long cityId,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Place> placePage = placeService.findAllPlacesByCityId(cityId, pageable);
        return placePreviewMapper.map(placePage, pageable);
    }

    @PostMapping
    public PlaceDto createPlace(@PathVariable("cityId") long cityId, @RequestBody @Valid CreatePlaceDto dto) {
        var city = cityService.findCityById(cityId).orElseThrow(() -> {
            throw new CityNotFoundException();
        });

        Place place = placeMapper.map(dto, city);
        placeService.savePlace(place);
        return placeMapper.map(place);
    }
}
