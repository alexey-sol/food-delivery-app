package com.github.alexeysol.common.feature.cart.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveCartItemDto {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "User ID is required")
    private Long userId;
}
