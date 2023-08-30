package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.mapper.CityMapper;
import com.github.alexeysol.app.service.CityService;
import com.github.alexeysol.common.model.dto.CityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/city", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

//    private final CityMapper cityMapper = CityMapper.INSTANCE;
    private final CityMapper cityMapper;

    @GetMapping
    public Set<CityDto> getCities() {
        return cityMapper.map(cityService.findAllCities());
    }
}
