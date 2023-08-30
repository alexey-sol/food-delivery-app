package com.github.alexeysol.common.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderItemDto {
    private long id;

    private int quantity;

    private Set<ProductDto> products; // TODO to singular

//    private OrderDto order;

    private Date createdAt;

    private Date updatedAt;
}
