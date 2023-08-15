package com.github.alexeysol.app.model.dto;

import lombok.Data;

@Data
public class StoreAddressDto {
    private long id;

    private String addressLine;

    private String city;
}
