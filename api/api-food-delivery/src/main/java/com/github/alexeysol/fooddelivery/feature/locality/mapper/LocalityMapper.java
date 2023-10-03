package com.github.alexeysol.fooddelivery.feature.locality.mapper;

import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocalityMapper {
    default List<LocalityDto> map(List<Locality> localities) {
        return localities.stream()
            .map(this::map)
            .toList();
    }

    LocalityDto map(Locality locality);

    Locality map(LocalityDto dto);

    Locality map(Long localityId);
}
