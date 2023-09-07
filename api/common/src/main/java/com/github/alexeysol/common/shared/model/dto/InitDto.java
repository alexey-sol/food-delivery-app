package com.github.alexeysol.common.shared.model.dto;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitDto { // TODO rename
    private UserDto profile;

    private List<CityDto> cities;
}
