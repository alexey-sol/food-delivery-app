package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CartDto {
    private long id;

    private long totalPrice;

    private PlaceDto place;

    private Set<CartItemDto> cartItems;

    private Date createdAt;

    private Date updatedAt;
}
