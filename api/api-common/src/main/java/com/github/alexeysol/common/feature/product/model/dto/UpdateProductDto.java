package com.github.alexeysol.common.feature.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    private String name;

    private String description;

    private Integer calories;

    private Long price;
}
