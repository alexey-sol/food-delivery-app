package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CartDto {
    private long id;

    private PlaceDto place;

    private long totalPrice;

    private List<CartItemDto> cartItems;

    private Date createdAt;

    private Date updatedAt;
}
