package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.common.model.dto.CreateStoreDto;
import com.github.alexeysol.common.model.dto.StoreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(uses = StoreAddressMapper.class)
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    default Page<StoreDto> map(Page<Store> storePage, Pageable pageable) {
        var storeDtoList = map(storePage.getContent());
        return new PageImpl<>(storeDtoList, pageable, storePage.getTotalElements());
    }

    default List<StoreDto> map(List<Store> stores) {
        return stores.stream()
            .map(this::map)
            .toList();
    }

    StoreDto map(Store store);

    Store map(StoreDto dto);

    @Mapping(target = "id", ignore = true)
    Store map(CreateStoreDto dto);
}
