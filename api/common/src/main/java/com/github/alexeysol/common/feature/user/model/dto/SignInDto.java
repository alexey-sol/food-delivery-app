package com.github.alexeysol.common.feature.user.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDto {
    @NotNull(message = "Phone is required and must not be blank")
    @Size(min = 11, message = "Phone must have minimum 11 characters")
    private String phone;

    @NotEmpty(message = "Password is required and must not be blank")
    private String password;
}
