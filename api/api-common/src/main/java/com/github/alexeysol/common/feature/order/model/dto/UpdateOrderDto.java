package com.github.alexeysol.common.feature.order.model.dto;

import com.github.alexeysol.common.shared.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDto {
    private OrderStatus status;
}
