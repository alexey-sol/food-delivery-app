package com.github.alexeysol.common.feature.user.model.dto;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import lombok.Data;

@Data
public class UserAddressDto {
    private Long id;

    private CityDto city;

    private String addressLine;
}
