package com.github.alexeysol.fooddelivery.feature.city.controller;

import com.github.alexeysol.fooddelivery.feature.city.mapper.CityMapper;
import com.github.alexeysol.fooddelivery.feature.city.service.CityService;
import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/city", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    private final CityMapper cityMapper;

    @GetMapping
    public List<CityDto> getCities() {
        return cityMapper.map(cityService.findAllCities());
    }
}
