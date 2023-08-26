package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class UpdateProductDto {
    private String name;

    private String description;

    private Integer calories;

    private Integer quantity;

    private Long price;
}
