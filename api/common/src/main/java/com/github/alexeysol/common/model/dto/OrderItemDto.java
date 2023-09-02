package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderItemDto {
    private long id;

    private int quantity;

    private ProductDto product;

//    private OrderDto order;

    private Date createdAt;

    private Date updatedAt;
}
