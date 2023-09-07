package com.github.alexeysol.common.feature.city.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {
    private Long id;

    private String name;
}
