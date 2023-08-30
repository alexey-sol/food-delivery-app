package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.Order;
import com.github.alexeysol.app.model.entity.User;
import com.github.alexeysol.app.service.StoreService;
import com.github.alexeysol.common.model.dto.CreateOrderDto;
import com.github.alexeysol.common.model.dto.OrderDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
    uses = { OrderItemMapper.class, StoreService.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default Set<OrderDto> map(Set<Order> orders) {
        return orders.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

//    @Mapping(target = "store", source = "store")
    OrderDto map(Order order);

    Order map(OrderDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "store", source = "dto.storeId") // TODO still need store id in dto
    @Mapping(target = "user", source = "user")
    Order map(CreateOrderDto dto, User user, @MappingTarget Order order); // TODO it seems that if i provide entity from ouside, as arg (MappingTarget order), then default fields don't get nullified. Fix everywhere
}
