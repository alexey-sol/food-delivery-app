package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class PlaceAddressDto {
    private Long id;

    private String addressLine;

    private CityDto city;
}
