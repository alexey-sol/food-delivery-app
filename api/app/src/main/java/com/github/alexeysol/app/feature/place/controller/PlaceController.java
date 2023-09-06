package com.github.alexeysol.app.feature.place.controller;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.app.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.city.service.CityService;
import com.github.alexeysol.app.feature.place.service.PlaceService;
import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/place", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class PlaceController {
    private static final String CITY = "City";
    private static final String PLACE = "Place";

    private final CityService cityService;
    private final PlaceService placeService;

//    private final PlaceMapper placeMapper = PlaceMapper.INSTANCE;
    private final PlaceMapper placeMapper;

    @GetMapping
    public Page<PlaceDto> getPlaces(
        @RequestParam Optional<Long> cityId,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Place> placePage;

        if (cityId.isPresent()) {
            placePage = getAllPlacesByCityId(cityId.get(), pageable);
        } else {
            placePage = getAllPlaces(pageable);
        }

        return placeMapper.map(placePage, pageable);
    }

    private Page<Place> getAllPlacesByCityId(long cityId, Pageable pageable) {
        return placeService.findAllPlacesByCityId(cityId, pageable);
    }

    private Page<Place> getAllPlaces(Pageable pageable) {
        return placeService.findAllPlaces(pageable);
    }

    @GetMapping("/{id}")
    public PlaceDto getPlaceById(@PathVariable long id) {
        var place = placeService.findPlaceById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return placeMapper.map(place);
    }

    @PostMapping
    public PlaceDto createPlace(@RequestBody @Valid CreatePlaceDto dto) {
        var cityId = dto.getAddress().getCityId();

        var city = cityService.findCityById(cityId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, CITY, cityId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

//        Place place = placeMapper.map(dto, city);
        Place place = placeMapper.map(dto, city);
//        place.getAddress().setCity(city); // TODO set city via mapper

        placeService.savePlace(place);
        return placeMapper.map(place);
    }

    @PatchMapping("/{id}")
    public PlaceDto updatePlaceById(@PathVariable long id, @RequestBody @Valid UpdatePlaceDto dto) {
        var place = placeService.findPlaceById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Place updatedPlace = placeMapper.map(dto, place);
        placeService.savePlace(updatedPlace);
        return placeMapper.map(updatedPlace);
    }
}
