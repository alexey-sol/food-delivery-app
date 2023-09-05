package com.github.alexeysol.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InitDto { // TODO rename
    private UserDto profile;

    private List<CityDto> cities;
}
