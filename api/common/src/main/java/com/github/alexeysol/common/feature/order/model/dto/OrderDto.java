package com.github.alexeysol.common.feature.order.model.dto;

import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.shared.model.OrderStatus;
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
