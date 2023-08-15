package com.github.alexeysol.app.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateStoreAddressDto {
    @NotEmpty(message = "Address line is required and must not be blank")
    private String addressLine;

    @NotEmpty(message = "City is required and must not be blank")
    private String city;
}
