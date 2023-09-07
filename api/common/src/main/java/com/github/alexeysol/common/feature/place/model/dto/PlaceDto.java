package com.github.alexeysol.common.feature.place.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PlaceDto {
    private Long id;

    private PlaceAddressDto address;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;
}
