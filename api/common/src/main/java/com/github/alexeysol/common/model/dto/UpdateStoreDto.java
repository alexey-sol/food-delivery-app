package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class UpdateStoreDto {
    private String name;

    private String description;

    private UpdateStoreAddressDto address;
}
