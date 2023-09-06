package com.github.alexeysol.app.feature.place.mapper;

import com.github.alexeysol.app.feature.city.mapper.CityMapper;
import com.github.alexeysol.app.feature.place.model.entity.PlaceAddress;
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

    // TODO there's a cleaner way to get an entity (city in this case) from the mapper itself: https://stackoverflow.com/a/48973435
    @Mapping(target = "id", ignore = true)
    PlaceAddress map(CreateAddressDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "place", ignore = true)
    PlaceAddress map(UpdatePlaceAddressDto dto, @MappingTarget PlaceAddress address);
}
