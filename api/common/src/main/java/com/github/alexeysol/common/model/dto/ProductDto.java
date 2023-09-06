package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private long id;

    private PlaceDto place;

    private Long price;

    private String name;

    private String description;

    private Integer calories;

    private Date createdAt;

    private Date updatedAt;
}
