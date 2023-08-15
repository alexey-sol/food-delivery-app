package com.github.alexeysol.app.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StoreDto {
    private long id;

    private String name;

    private String description;

    private StoreAddressDto address;

    private Date createdAt;

    private Date updatedAt;
}
