package com.github.alexeysol.common.shared.model.dto;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InitDto { // TODO rename
    private UserDto profile;

    private List<CityDto> cities;
}
