package com.github.alexeysol.common.feature.product.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    @NotEmpty(message = "Name is required and must not be blank")
    private String name;

    private String description;

    private Integer calories;

    @NotNull(message = "Price is required")
    private Long price;

    @NotNull(message = "Place ID is required")
    private Long placeId;
}
