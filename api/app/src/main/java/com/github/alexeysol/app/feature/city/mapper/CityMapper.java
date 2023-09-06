package com.github.alexeysol.app.feature.city.mapper;

import com.github.alexeysol.app.feature.city.model.entity.City;
import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper {
    default List<CityDto> map(List<City> cities) {
        return cities.stream()
            .map(this::map)
            .toList();
    }

    CityDto map(City city);

    City map(CityDto dto);

    City map(Long cityId);
}
