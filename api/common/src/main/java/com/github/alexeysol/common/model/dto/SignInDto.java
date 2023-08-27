package com.github.alexeysol.common.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDto {
//    @NotEmpty(message = "Username is required and must not be blank") // TODO what about signing in with phone?
//    private String username;
    @NotNull(message = "Phone is required and must not be blank")
    @Size(min = 11, message = "Phone must have minimum 11 characters")
    private String phone;

    @NotEmpty(message = "Password is required and must not be blank")
    private String password;
}
