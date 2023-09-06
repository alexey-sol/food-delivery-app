package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class UserAddressDto {
    private Long id;

    private CityDto city;

    private String addressLine;
}
