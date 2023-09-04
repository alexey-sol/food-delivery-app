package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlaceDto {
    private Long id;

    private String name;

    private String description;

    private PlaceAddressDto address;

    private Date createdAt;

    private Date updatedAt;
}
