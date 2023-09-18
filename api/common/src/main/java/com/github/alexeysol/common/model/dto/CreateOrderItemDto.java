package com.github.alexeysol.common.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateOrderItemDto {
//    @NotNull(message = "Product ID is required")
//    private Long productId;
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}
