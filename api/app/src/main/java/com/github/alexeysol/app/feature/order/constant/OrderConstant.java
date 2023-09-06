package com.github.alexeysol.app.feature.order.constant;

import com.github.alexeysol.common.shared.model.OrderStatus;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class OrderConstant {
    public final List<OrderStatus> ACTIVE_STATUSES = List.of(OrderStatus.PROCESSING, OrderStatus.DELIVERING);
}
