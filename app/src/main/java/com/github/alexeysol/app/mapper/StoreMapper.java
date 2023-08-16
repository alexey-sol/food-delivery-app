package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.dto.CreateStoreDto;
import com.github.alexeysol.app.model.dto.StoreDto;
import com.github.alexeysol.app.model.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = StoreAddressMapper.class)
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    default Set<StoreDto> map(Set<Store> stores) {
        return stores.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

    StoreDto map(Store store);

    Store map(StoreDto dto);

    @Mapping(target = "id", ignore = true)
    Store map(CreateStoreDto dto);
}
