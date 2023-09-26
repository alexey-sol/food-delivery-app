package com.github.alexeysol.common.feature.order.model.dto;

import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import com.github.alexeysol.common.shared.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;

    private PlacePreviewDto place;

    private long totalPrice;

    private OrderStatus status;

    private List<OrderItemDto> orderItems;

    private Date createdAt;

    private Date updatedAt;
}
