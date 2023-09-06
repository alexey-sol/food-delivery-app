package com.github.alexeysol.app.feature.order.mapper;

import com.github.alexeysol.app.feature.order.model.entity.Order;
import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.app.feature.place.service.PlaceService;
import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
    uses = { OrderItemMapper.class, PlaceService.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default List<OrderDto> map(List<Order> orders) {
        return orders.stream()
            .map(this::map)
            .toList();
    }

//    @Mapping(target = "place", source = "place")
    OrderDto map(Order order);

    Order map(OrderDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "place", source = "dto.placeId") // TODO still need place id in dto
    @Mapping(target = "user", source = "user")
    Order map(CreateOrderDto dto, User user, @MappingTarget Order order); // TODO it seems that if i provide entity from ouside, as arg (MappingTarget order), then default fields don't get nullified. Fix everywhere

    @Mapping(target = "id", ignore = true)
    Order map(UpdateOrderDto dto, @MappingTarget Order order);
}
