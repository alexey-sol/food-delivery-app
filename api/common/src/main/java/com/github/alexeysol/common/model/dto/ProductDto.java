package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private long id;

    private String name;

    private String description;

    private Integer calories;

    private Integer quantity;

    private Long price;

    private PlaceDto place;

    private Date createdAt;

    private Date updatedAt;
}
