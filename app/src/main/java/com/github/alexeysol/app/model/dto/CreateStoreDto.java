package com.github.alexeysol.app.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateStoreDto {
    @NotEmpty(message = "Name is required and must not be blank")
    private String name;

    private String description;
}
