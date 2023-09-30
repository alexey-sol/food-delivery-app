package com.github.alexeysol.common.shared.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDto {
    @NotEmpty(message = "Address line is required and must not be blank")
    private String addressLine;
}
