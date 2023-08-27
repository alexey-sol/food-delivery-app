package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.Cart;
import com.github.alexeysol.common.model.dto.CartDto;
import com.github.alexeysol.common.model.dto.SaveCartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CartItemMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDto map(Cart cart);

    Cart map(CartDto dto);

    // TODO ?need
//    @Mapping(target = "id", ignore = true)
//    Cart map(SaveCartItemDto dto);
}
