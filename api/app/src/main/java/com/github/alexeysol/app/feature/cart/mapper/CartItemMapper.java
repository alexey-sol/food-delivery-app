package com.github.alexeysol.app.feature.cart.mapper;

import com.github.alexeysol.app.feature.cart.model.entity.CartItem;
import com.github.alexeysol.app.feature.product.mapper.ProductMapper;
import com.github.alexeysol.common.feature.cart.model.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(uses = ProductMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartItemMapper {
    // TODO ignore cartItem.product.place

    // TODO return to list later if it'll work out
    default List<CartItemDto> map(List<CartItem> cartItems) {
        return cartItems.stream()
            .map(this::map)
            .toList();
    }
//    default List<CartItemDto> map(List<CartItem> cartItems) {
//        return cartItems.stream()
//            .map(this::map)
//            .toList();
//    }

    // TODO it doesnt add field to DTO but there are still redundant joins when querying DB
    // the field is still present in the result although it's null
    // maybe i should add extra mapper and extra DTOs for mapping product inside cart item (where i skip product.place field)
//    @Mapping(target = "product.place", ignore = true)
    CartItemDto map(CartItem cartItem);

    CartItem map(CartItemDto dto);
}
