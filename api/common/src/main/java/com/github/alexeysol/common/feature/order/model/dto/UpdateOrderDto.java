package com.github.alexeysol.common.feature.order.model.dto;

import com.github.alexeysol.common.shared.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderDto {
    private OrderStatus status;
}
