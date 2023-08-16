package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.app.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public Set<Store> findAllStores() {
        return new HashSet<>(storeRepository.findAll());
    }

    public Set<Store> findAllStoresByCity(String city) {
        return new HashSet<>(storeRepository.findAllByAddressCityIgnoreCase(city));
    }

    public Optional<Store> findStoreById(long id) {
        return storeRepository.findById(id);
    }

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }
}
