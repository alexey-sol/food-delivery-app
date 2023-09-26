package com.github.alexeysol.common.feature.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    private UserAddressDto address;

    private String phone;

    private String username;

    private Date createdAt;

    private Date updatedAt;
}
