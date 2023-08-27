package com.github.alexeysol.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class InitDto { // TODO rename
    private UserDto profile;

    private Set<CityDto> cities;
}
