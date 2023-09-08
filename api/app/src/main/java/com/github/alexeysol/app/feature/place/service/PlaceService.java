package com.github.alexeysol.app.feature.place.service;

import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.app.feature.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Page<Place> findAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable);
    }

    public Page<Place> findAllPlacesByCityId(long cityId, Pageable pageable) {
        return placeRepository.findAllByAddressCityId(cityId, pageable);
    }

    public Optional<Place> findPlaceById(long id) {
        return placeRepository.findById(id);
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    // TODO test for mapping:
    public Place findById(Long id) {
        return placeRepository.findById(id).orElse(null);
    }
}
