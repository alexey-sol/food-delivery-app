package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.City;
import com.github.alexeysol.common.model.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    default Set<CityDto> map(Set<City> cities) {
        return cities.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

    CityDto map(City city);

    City map(CityDto dto);

    City map(Long cityId);
}
