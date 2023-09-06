package com.github.alexeysol.common.feature.place.model.dto;

import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePlaceDto {
    @NotEmpty(message = "Name is required and must not be blank")
    private String name;

    private String description;

    @Valid
    @NotNull(message = "Address is required")
    private CreateAddressDto address;
}
