package com.github.alexeysol.app.feature.cart.mapper;

import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import com.github.alexeysol.app.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = { CartItemMapper.class, PlaceMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    default List<CartDto> map(List<Cart> carts) {
        return carts.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    CartDto map(Cart cart);

    Cart map(CartDto dto);

    // TODO ?need
//    @Mapping(target = "id", ignore = true)
//    Cart map(SaveCartItemDto dto);
}
