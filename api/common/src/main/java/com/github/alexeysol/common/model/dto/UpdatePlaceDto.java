package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class UpdatePlaceDto {
    private String name;

    private String description;

    private UpdatePlaceAddressDto address;
}
