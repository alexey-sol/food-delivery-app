package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.Order;
import com.github.alexeysol.app.model.entity.User;
import com.github.alexeysol.app.service.PlaceService;
import com.github.alexeysol.common.model.dto.CreateOrderDto;
import com.github.alexeysol.common.model.dto.OrderDto;
import com.github.alexeysol.common.model.dto.UpdateOrderDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
