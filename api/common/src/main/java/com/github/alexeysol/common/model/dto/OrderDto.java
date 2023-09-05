package com.github.alexeysol.common.model.dto;

import com.github.alexeysol.common.model.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private long id;

    private long totalPrice;

    private PlaceDto place;

    private OrderStatus status;

    private List<OrderItemDto> orderItems;

    private Date createdAt;

    private Date updatedAt;
}
