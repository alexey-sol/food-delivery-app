package com.github.alexeysol.common.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateOrderDto {
    @Valid
    @NotNull(message = "Order items are required")
    private Set<CreateOrderItemDto> orderItems;

    @NotNull(message = "Place ID is required")
    private Long placeId;

    @NotNull(message = "User ID is required")
    private Long userId;
}
