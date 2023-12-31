package com.github.alexeysol.common.feature.cart.model.dto;

import com.github.alexeysol.common.feature.product.model.dto.ProductPreviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private long id;

    private ProductPreviewDto product;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;
}
