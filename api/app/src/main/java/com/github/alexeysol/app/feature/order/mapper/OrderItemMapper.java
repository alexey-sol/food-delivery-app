package com.github.alexeysol.app.feature.order.mapper;

import com.github.alexeysol.app.feature.product.mapper.ProductMapper;
import com.github.alexeysol.app.feature.order.model.entity.OrderItem;
import com.github.alexeysol.app.feature.product.service.ProductService;
import com.github.alexeysol.common.feature.order.model.dto.CreateOrderItemDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
    uses = { ProductMapper.class, ProductService.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderItemMapper {
    default List<OrderItemDto> map(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(this::map)
            .toList();
    }

    OrderItemDto map(OrderItem orderItem);

    OrderItem map(OrderItemDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId")
    OrderItem map(CreateOrderItemDto dto);
}
