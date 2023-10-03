package com.github.alexeysol.fooddelivery.feature.user.mapper;

import com.github.alexeysol.fooddelivery.feature.cart.mapper.CartMapper;
import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    uses = { CartMapper.class, UserAddressMapper.class },
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    UserDto map(User user);

    User map(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.addressLine", source = "dto.address.addressLine")
    @Mapping(target = "address.locality", source = "locality")
    User map(SignUpDto dto, Locality locality);
}
