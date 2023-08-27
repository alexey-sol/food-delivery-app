package com.github.alexeysol.common.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveCartItemDto {
    @NotEmpty(message = "Product ID is required and must not be blank")
    private long productId;

    @NotEmpty(message = "Quantity is required and must not be blank")
    private int quantity;

    @NotEmpty(message = "User ID is required and must not be blank")
    private long userId;
}
