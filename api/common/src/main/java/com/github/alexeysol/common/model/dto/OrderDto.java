package com.github.alexeysol.common.model.dto;

import com.github.alexeysol.common.model.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private long id;

    private PlaceDto place;

    private long totalPrice;

    private OrderStatus status;

    private List<OrderItemDto> orderItems;

    private Date createdAt;

    private Date updatedAt;
}
