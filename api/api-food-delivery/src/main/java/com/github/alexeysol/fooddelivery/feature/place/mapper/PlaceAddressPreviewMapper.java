package com.github.alexeysol.fooddelivery.feature.place.mapper;

import com.github.alexeysol.common.feature.place.model.dto.PlaceAddressPreviewDto;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.PlaceAddress;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlaceAddressPreviewMapper {
    PlaceAddressPreviewDto map(PlaceAddress address);
}
