package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CartItemDto {
    private long id;

    private int quantity;

    private ProductDto product;

    private Date createdAt;

    private Date updatedAt;
}
