package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDto {
    private long id;

    private String phone;

    private String username;

    private UserAddressDto address;

    private Set<CartDto> carts;

    private Date createdAt;

    private Date updatedAt;
}
