package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.City;
import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.common.model.dto.CreateStoreDto;
import com.github.alexeysol.common.model.dto.StoreDto;
import com.github.alexeysol.common.model.dto.UpdateStoreDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    uses = StoreAddressMapper.class,
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    default Page<StoreDto> map(Page<Store> storePage, Pageable pageable) {
        var storeDtoList = map(storePage.getContent());
        return new PageImpl<>(storeDtoList, pageable, storePage.getTotalElements());
    }

    default List<StoreDto> map(List<Store> stores) {
        return stores.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    StoreDto map(Store store);

    Store map(StoreDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "address.addressLine", source = "dto.address.addressLine") // [1]
    @Mapping(target = "address.city", source = "city")
    Store map(CreateStoreDto dto, City city);

    @Mapping(target = "id", ignore = true)
    Store map(UpdateStoreDto dto, @MappingTarget Store store);
}

// [1]. Without "disableBuilder = true", can't map address anymore in combination with mapping address.city.
