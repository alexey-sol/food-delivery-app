package com.github.alexeysol.fooddelivery.feature.locality.service;

import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
import com.github.alexeysol.fooddelivery.feature.locality.repository.LocalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalityService {
    private final LocalityRepository localityRepository;

    public List<Locality> findAllLocalities() {
        return localityRepository.findAll();
    }

    public Optional<Locality> findLocalityById(long id) {
        return localityRepository.findById(id);
    }
}
