package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.StoreAddress;
import com.github.alexeysol.common.model.dto.CreateStoreAddressDto;
import com.github.alexeysol.common.model.dto.StoreAddressDto;
import com.github.alexeysol.common.model.dto.UpdateStoreAddressDto;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoreAddressMapper {
    StoreAddressDto map(StoreAddress address);

    StoreAddress map(StoreAddressDto dto);

    @Mapping(target = "id", ignore = true)
    StoreAddress map(CreateStoreAddressDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "store", ignore = true)
    StoreAddress map(UpdateStoreAddressDto dto, @MappingTarget StoreAddress address);
}
