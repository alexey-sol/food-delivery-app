package com.github.alexeysol.common.model.dto;

import com.github.alexeysol.common.model.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderDto {
    private OrderStatus status;
}
