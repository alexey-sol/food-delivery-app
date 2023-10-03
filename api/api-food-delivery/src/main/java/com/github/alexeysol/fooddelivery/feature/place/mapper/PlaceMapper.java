package com.github.alexeysol.fooddelivery.feature.place.mapper;

import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import org.mapstruct.*;

@Mapper(
    uses = PlaceAddressMapper.class,
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PlaceMapper {
    PlaceDto map(Place place);

    Place map(PlaceDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "address.addressLine", source = "dto.address.addressLine") // [1]
    @Mapping(target = "address.locality", source = "locality")
    Place map(CreatePlaceDto dto, Locality locality);

    @Mapping(target = "id", ignore = true)
    Place map(UpdatePlaceDto dto, @MappingTarget Place place);
}

// [1]. Without "disableBuilder = true", can't map address anymore in combination with mapping address.locality.
