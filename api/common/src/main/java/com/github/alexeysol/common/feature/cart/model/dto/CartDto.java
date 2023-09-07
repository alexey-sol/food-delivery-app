package com.github.alexeysol.common.feature.cart.model.dto;

import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class CartDto {
    private long id;

    private PlaceDto place;

    private long totalPrice;

    private List<CartItemDto> cartItems;

    private Date createdAt;

    private Date updatedAt;
}
