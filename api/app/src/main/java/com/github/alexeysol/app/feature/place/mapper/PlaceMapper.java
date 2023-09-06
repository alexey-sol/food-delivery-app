package com.github.alexeysol.app.feature.place.mapper;

import com.github.alexeysol.app.feature.city.model.entity.City;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.common.feature.place.model.dto.CreatePlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    uses = PlaceAddressMapper.class,
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PlaceMapper {
    PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

    default Page<PlaceDto> map(Page<Place> placePage, Pageable pageable) {
        var placeDtoList = map(placePage.getContent());
        return new PageImpl<>(placeDtoList, pageable, placePage.getTotalElements());
    }

    default List<PlaceDto> map(List<Place> places) {
        return places.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    PlaceDto map(Place place);

    Place map(PlaceDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "address.addressLine", source = "dto.address.addressLine") // [1]
    @Mapping(target = "address.city", source = "city")
    Place map(CreatePlaceDto dto, City city);

    @Mapping(target = "id", ignore = true)
    Place map(UpdatePlaceDto dto, @MappingTarget Place place);
}

// [1]. Without "disableBuilder = true", can't map address anymore in combination with mapping address.city.
