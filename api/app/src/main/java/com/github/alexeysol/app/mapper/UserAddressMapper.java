package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.UserAddress;
import com.github.alexeysol.common.model.dto.CreateAddressDto;
import com.github.alexeysol.common.model.dto.UserAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = CityMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserAddressMapper {
    UserAddressDto map(UserAddress address);

    UserAddress map(UserAddressDto dto);

    @Mapping(target = "id", ignore = true)
    UserAddress map(CreateAddressDto dto);
}
