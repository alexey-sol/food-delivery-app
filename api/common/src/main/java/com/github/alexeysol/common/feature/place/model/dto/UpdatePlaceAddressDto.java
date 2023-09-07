package com.github.alexeysol.common.feature.place.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePlaceAddressDto {
    private String addressLine;
}
