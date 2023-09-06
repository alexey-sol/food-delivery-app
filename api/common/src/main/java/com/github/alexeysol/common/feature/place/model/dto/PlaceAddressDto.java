package com.github.alexeysol.common.feature.place.model.dto;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import lombok.Data;

@Data
public class PlaceAddressDto {
    private Long id;

    private CityDto city;

    private String addressLine;
}
