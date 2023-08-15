package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.dto.CreateStoreAddressDto;
import com.github.alexeysol.app.model.dto.StoreAddressDto;
import com.github.alexeysol.app.model.entity.StoreAddress;
import org.mapstruct.Mapper;

@Mapper
public interface StoreAddressMapper {
    StoreAddressDto map(StoreAddress address);

    StoreAddress map(StoreAddressDto dto);

    StoreAddress map(CreateStoreAddressDto dto);
}
