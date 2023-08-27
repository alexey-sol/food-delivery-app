package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.app.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public Page<Store> findAllStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public Page<Store> findAllStoresByCityId(long cityId, Pageable pageable) {
        return storeRepository.findAllByAddressCityId(cityId, pageable);
    }

    public Optional<Store> findStoreById(long id) {
        return storeRepository.findById(id);
    }

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }
}
