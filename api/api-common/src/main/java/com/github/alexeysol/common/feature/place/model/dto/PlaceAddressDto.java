package com.github.alexeysol.common.feature.place.model.dto;

import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceAddressDto {
    private Long id;

    private LocalityDto locality;

    private String addressLine;
}
