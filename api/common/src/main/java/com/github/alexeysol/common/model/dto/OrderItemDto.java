package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderItemDto {
    private long id;

    private ProductDto product;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;
}
