package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.City;
import com.github.alexeysol.app.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public Set<City> findAllCities() {
        return new HashSet<>(cityRepository.findAll());
    }

    public Optional<City> findCityById(long id) {
        return cityRepository.findById(id);
    }
}
