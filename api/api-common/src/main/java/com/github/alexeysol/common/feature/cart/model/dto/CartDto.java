package com.github.alexeysol.common.feature.cart.model.dto;

import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private long id;

    private PlacePreviewDto place;

    private long totalPrice;

    private List<CartItemDto> cartItems;

    private Date createdAt;

    private Date updatedAt;
}
