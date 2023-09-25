package com.github.alexeysol.fooddelivery.feature.place.mapper;

import com.github.alexeysol.fooddelivery.feature.city.mapper.CityMapper;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.PlaceAddress;
import com.github.alexeysol.common.shared.model.dto.CreateAddressDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceAddressDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = CityMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlaceAddressMapper {
    PlaceAddressDto map(PlaceAddress address);

    PlaceAddress map(PlaceAddressDto dto);

    @Mapping(target = "id", ignore = true)
    PlaceAddress map(CreateAddressDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "place", ignore = true)
    PlaceAddress map(UpdatePlaceAddressDto dto, @MappingTarget PlaceAddress address);
}
