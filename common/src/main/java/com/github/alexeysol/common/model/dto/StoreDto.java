package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StoreDto {
    private Long id;

    private String name;

    private String description;

    private StoreAddressDto address;

    private Date createdAt;

    private Date updatedAt;
}
