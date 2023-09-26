package com.github.alexeysol.common.feature.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPreviewDto {
    private long id;

    private Long price;

    private String name;

    private String description;

    private Integer calories;

    private Date createdAt;

    private Date updatedAt;
}
