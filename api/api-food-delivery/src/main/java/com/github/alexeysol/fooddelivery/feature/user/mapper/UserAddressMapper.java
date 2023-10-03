package com.github.alexeysol.fooddelivery.feature.user.mapper;

import com.github.alexeysol.fooddelivery.feature.locality.mapper.LocalityMapper;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.UserAddress;
import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
import com.github.alexeysol.common.feature.user.model.dto.UserAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = LocalityMapper.class,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserAddressMapper {
    UserAddressDto map(UserAddress address);

    UserAddress map(UserAddressDto dto);

    @Mapping(target = "id", ignore = true)
    UserAddress map(CreateAddressDto dto);
}
