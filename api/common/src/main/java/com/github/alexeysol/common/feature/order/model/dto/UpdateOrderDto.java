package com.github.alexeysol.common.feature.order.model.dto;

import com.github.alexeysol.common.shared.model.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderDto {
    private OrderStatus status;
}
