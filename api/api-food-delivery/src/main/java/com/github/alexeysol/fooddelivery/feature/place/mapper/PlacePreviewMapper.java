package com.github.alexeysol.fooddelivery.feature.place.mapper;

import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    uses = PlaceAddressPreviewMapper.class,
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PlacePreviewMapper {
    default Page<PlacePreviewDto> map(Page<Place> placePage, Pageable pageable) {
        var placeDtoList = map(placePage.getContent());
        return new PageImpl<>(placeDtoList, pageable, placePage.getTotalElements());
    }

    default List<PlacePreviewDto> map(List<Place> places) {
        return places.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    PlacePreviewDto map(Place place);
}
