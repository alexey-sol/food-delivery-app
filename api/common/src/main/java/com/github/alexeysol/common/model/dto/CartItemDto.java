package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CartItemDto {
    private long id;

    private ProductDto product;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;
}
