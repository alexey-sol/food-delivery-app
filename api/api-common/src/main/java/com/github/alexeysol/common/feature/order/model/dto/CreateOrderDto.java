package com.github.alexeysol.common.feature.order.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    @Valid
    @NotNull(message = "Order items are required")
    private List<CreateOrderItemDto> orderItems;

    @NotNull(message = "Place ID is required")
    private Long placeId;
}
