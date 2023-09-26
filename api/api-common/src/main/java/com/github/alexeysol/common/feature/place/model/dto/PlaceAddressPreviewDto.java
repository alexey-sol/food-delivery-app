package com.github.alexeysol.common.feature.place.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceAddressPreviewDto {
    private Long id;

    private String addressLine;
}
