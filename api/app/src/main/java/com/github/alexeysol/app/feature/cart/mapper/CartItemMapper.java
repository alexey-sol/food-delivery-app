package com.github.alexeysol.app.feature.cart.mapper;

import com.github.alexeysol.app.feature.cart.model.entity.Cart;
import com.github.alexeysol.app.feature.cart.model.entity.CartItem;
import com.github.alexeysol.app.feature.product.model.entity.Product;
import com.github.alexeysol.common.feature.cart.model.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartItemMapper {
    default List<CartItemDto> map(List<CartItem> cartItems) {
        return cartItems.stream()
            .map(this::map)
            .toList();
    }

    CartItemDto map(CartItem cartItem);

    CartItem map(CartItemDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "product", source = "product")
    @Mapping(target = "cart", source = "cart")
    CartItem map(Cart cart, Product product, @MappingTarget CartItem cartItem);
}
