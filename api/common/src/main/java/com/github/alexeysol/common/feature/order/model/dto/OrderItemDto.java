package com.github.alexeysol.common.feature.order.model.dto;

import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import lombok.Data;

import java.util.Date;

@Data
public class OrderItemDto {
    private long id;

    private ProductDto product;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;
}
