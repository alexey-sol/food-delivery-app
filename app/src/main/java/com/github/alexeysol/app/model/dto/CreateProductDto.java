package com.github.alexeysol.app.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductDto {
    @NotEmpty(message = "Name is required and must not be blank")
    private String name;

    private String description;

    private int calories;

    @NotNull(message = "Quantity is required")
    private int quantity;

    @NotNull(message = "Price is required")
    private long price;

    @NotNull(message = "Store ID is required")
    private long storeId;
}
