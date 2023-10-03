package com.github.alexeysol.fooddelivery.feature.locality.controller;

import com.github.alexeysol.fooddelivery.feature.locality.mapper.LocalityMapper;
import com.github.alexeysol.fooddelivery.feature.locality.service.LocalityService;
import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/locality", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class LocalityController {
    private final LocalityService localityService;

    private final LocalityMapper localityMapper;

    @GetMapping
    public List<LocalityDto> getLocalities() {
        return localityMapper.map(localityService.findAllLocalities());
    }
}
