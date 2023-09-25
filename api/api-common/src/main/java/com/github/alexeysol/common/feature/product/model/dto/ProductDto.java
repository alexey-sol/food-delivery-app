package com.github.alexeysol.common.feature.product.model.dto;

import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;

    private PlaceDto place;

    private Long price;

    private String name;

    private String description;

    private Integer calories;

    private Date createdAt;

    private Date updatedAt;
}
