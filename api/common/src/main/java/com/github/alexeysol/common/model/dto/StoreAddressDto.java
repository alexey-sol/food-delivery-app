package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class StoreAddressDto {
    private Long id;

    private String addressLine;

    private String city;
}
