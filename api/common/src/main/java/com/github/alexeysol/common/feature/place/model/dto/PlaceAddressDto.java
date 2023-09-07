package com.github.alexeysol.common.feature.place.model.dto;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceAddressDto {
    private Long id;

    private CityDto city;

    private String addressLine;
}
