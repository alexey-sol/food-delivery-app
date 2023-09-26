package com.github.alexeysol.common.feature.place.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlacePreviewDto {
    private Long id;

    private PlaceAddressPreviewDto address;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;
}
