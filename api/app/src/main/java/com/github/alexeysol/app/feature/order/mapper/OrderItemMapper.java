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
    componentModel = "spring", // TODO remove?
    uses = { ProductMapper.class, ProductService.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderItemMapper {
    default List<OrderItemDto> map(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(this::map)
            .toList();
    }

//    @Mapping(target = "order", ignore = true) // TODO either ignore = true here or delete field in DTO, otherwise there's a recursion
    // TODO transform products to one product (get first one)
//    @Mapping(target = "product", source = "products", qualifiedByName = "product")
//    @Mapping(target = "product", expression = "java(products.stream().findFirst().isPresent())")
    OrderItemDto map(OrderItem orderItem);

    OrderItem map(OrderItemDto dto);

    default Set<OrderItem> mapOnCreate(Set<CreateOrderItemDto> dtoSet) { // TODO rename?
        return dtoSet.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "products", source = "productId")
////    @Mapping(target = "order.orderItems", ignore = true) // TODO how to deal with recursion error?  https://stackoverflow.com/questions/60828011/when-is-used-onetomany-and-manytoone-and-with-mapstruct-i-get-recursion-infi
//    OrderItem map(CreateOrderItemDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId")
//    @Mapping(target = "order.orderItems", ignore = true) // TODO how to deal with recursion error?  https://stackoverflow.com/questions/60828011/when-is-used-onetomany-and-manytoone-and-with-mapstruct-i-get-recursion-infi
    OrderItem map(CreateOrderItemDto dto); // TODO use @MappingTarget
}
