package com.github.alexeysol.app.feature.cart.mapper;

import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import com.github.alexeysol.app.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.place.service.PlaceService;
import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.app.feature.user.service.UserService;
import com.github.alexeysol.common.feature.cart.model.dto.CartDto;
import com.github.alexeysol.common.feature.cart.model.dto.SaveCartItemDto;
import com.github.alexeysol.common.shared.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(
    uses = { CartItemMapper.class, PlaceMapper.class, MapperUtil.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CartMapper {
    default List<CartDto> map(List<Cart> carts) {
        return carts.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    CartDto map(Cart cart);

    Cart map(CartDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "place", source = "place")
    Cart map(User user, Place place, @MappingTarget Cart cart);

//    default <T> T unwrapOptional(Optional<T> optional) {
//        return optional.orElse(null);
//    }
    // TODO ?need
//    @Mapping(target = "id", ignore = true)
//    Cart map(SaveCartItemDto dto);
}
