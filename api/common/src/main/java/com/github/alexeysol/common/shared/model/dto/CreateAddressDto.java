package com.github.alexeysol.common.shared.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAddressDto {
    @NotEmpty(message = "Address line is required and must not be blank")
    private String addressLine;

    @NotNull(message = "City ID is required")
    private Long cityId;
}
