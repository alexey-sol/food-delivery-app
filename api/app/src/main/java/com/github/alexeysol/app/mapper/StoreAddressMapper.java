package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.StoreAddress;
import com.github.alexeysol.common.model.dto.CreateAddressDto;
import com.github.alexeysol.common.model.dto.StoreAddressDto;
import com.github.alexeysol.common.model.dto.UpdateStoreAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = CityMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoreAddressMapper {
    StoreAddressDto map(StoreAddress address);

    StoreAddress map(StoreAddressDto dto);

    // TODO there's a cleaner way to get an entity (city in this case) from the mapper itself: https://stackoverflow.com/a/48973435
    @Mapping(target = "id", ignore = true)
    StoreAddress map(CreateAddressDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "store", ignore = true)
    StoreAddress map(UpdateStoreAddressDto dto, @MappingTarget StoreAddress address);
}
