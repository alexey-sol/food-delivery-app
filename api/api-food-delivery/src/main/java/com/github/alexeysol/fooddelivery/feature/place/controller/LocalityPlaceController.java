package com.github.alexeysol.fooddelivery.feature.place.controller;

import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import com.github.alexeysol.common.feature.locality.exception.LocalityNotFoundException;
import com.github.alexeysol.fooddelivery.feature.locality.service.LocalityService;
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
@RequestMapping(value = "/locality/{localityId}/place", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class LocalityPlaceController {
    private final LocalityService localityService;
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final PlacePreviewMapper placePreviewMapper;

    @GetMapping
    public Page<PlacePreviewDto> getPlacesByLocalityId(
        @PathVariable("localityId") long localityId,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Place> placePage = placeService.findAllPlacesByLocalityId(localityId, pageable);
        return placePreviewMapper.map(placePage, pageable);
    }

    @PostMapping
    public PlaceDto createPlace(@PathVariable("localityId") long localityId, @RequestBody @Valid CreatePlaceDto dto) {
        var locality = localityService.findLocalityById(localityId).orElseThrow(() -> {
            throw new LocalityNotFoundException();
        });

        Place place = placeMapper.map(dto, locality);
        placeService.savePlace(place);
        return placeMapper.map(place);
    }
}
