package com.github.alexeysol.common.shared.model.dto;

import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitDto {
    private UserDto profile;

    private List<LocalityDto> cities;
}
