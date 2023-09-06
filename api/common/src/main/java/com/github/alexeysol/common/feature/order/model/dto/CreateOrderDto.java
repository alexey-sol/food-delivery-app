package com.github.alexeysol.common.feature.order.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    @Valid
    @NotNull(message = "Order items are required")
    private List<CreateOrderItemDto> orderItems;

    @NotNull(message = "Place ID is required")
    private Long placeId;

    @NotNull(message = "User ID is required")
    private Long userId;
}
