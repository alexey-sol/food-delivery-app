package com.github.alexeysol.common.feature.user.model.dto;

import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDto {
    private Long id;

    private LocalityDto locality;

    private String addressLine;
}
