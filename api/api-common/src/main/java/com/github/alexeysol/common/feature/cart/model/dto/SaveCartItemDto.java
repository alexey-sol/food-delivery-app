package com.github.alexeysol.common.feature.cart.model.dto;

import com.github.alexeysol.common.feature.cart.model.SaveCartItemOperation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCartItemDto {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Place ID is required")
    private Long placeId;

    @NotNull(message = "Count is required")
    private Integer count;

    @NotNull(message = "Count is required")
    private SaveCartItemOperation operation;
}
