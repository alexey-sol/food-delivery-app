package com.github.alexeysol.common.feature.place.model.dto;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceAddressDto {
    private Long id;

    private CityDto city;

    private String addressLine;
}
