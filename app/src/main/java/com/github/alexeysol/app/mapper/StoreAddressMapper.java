package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.StoreAddress;
import com.github.alexeysol.common.model.dto.CreateStoreAddressDto;
import com.github.alexeysol.common.model.dto.StoreAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StoreAddressMapper {
    StoreAddressDto map(StoreAddress address);

    StoreAddress map(StoreAddressDto dto);

    @Mapping(target = "id", ignore = true)
    StoreAddress map(CreateStoreAddressDto dto);
}
