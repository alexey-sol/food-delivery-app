package com.github.alexeysol.common.model.dto;

import com.github.alexeysol.common.model.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    private long id;

    private long totalPrice;

//    private UserDto user;

    private StoreDto store;

    private OrderStatus status;

    private Set<OrderItemDto> orderItems = new HashSet<>();

    private Date createdAt;

    private Date updatedAt;
}
