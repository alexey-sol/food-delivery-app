package com.github.alexeysol.common.feature.user.model.dto;

import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotNull(message = "Phone is required and must not be blank")
    @Size(min = 11, message = "Phone must have minimum 11 characters")
    private String phone;

    private String username;

    @Valid
    @NotNull(message = "Address is required")
    private CreateAddressDto address;

    @NotEmpty(message = "Password is required and must not be blank")
    private String password;
}
