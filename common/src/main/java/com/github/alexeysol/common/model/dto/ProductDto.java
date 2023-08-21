package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private long id;

    private String name;

    private String description;

    private int calories;

    private int quantity;

    private long price;

    private StoreDto store;

    private Date createdAt;

    private Date updatedAt;
}
