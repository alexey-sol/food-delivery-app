package com.github.alexeysol.common.feature.user.model.dto;

import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private long id;

    private UserAddressDto address;

    private String phone;

    private String username;

    private List<CartDto> carts;

    private Date createdAt;

    private Date updatedAt;
}
