package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.CartItem;
import com.github.alexeysol.common.model.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = ProductMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartItemMapper {
    // TODO ignore cartItem.product.store

    // TODO it doesnt add field to DTO but there are still redundant joins when querying DB
    // the field is still present in the result although it's null
    // maybe i should add extra mapper and extra DTOs for mapping product inside cart item (where i skip product.store field)
    @Mapping(target = "product.store", ignore = true)
    CartItemDto map(CartItem cartItem);

    CartItem map(CartItemDto dto);
}
