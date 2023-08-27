package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.City;
import com.github.alexeysol.app.model.entity.User;
import com.github.alexeysol.common.model.dto.SignUpDto;
import com.github.alexeysol.common.model.dto.UserDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    uses = { CartMapper.class, UserAddressMapper.class },
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto map(User user);

    User map(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.addressLine", source = "dto.address.addressLine")
    @Mapping(target = "address.city", source = "city")
    User map(SignUpDto dto, City city);
}
