package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.StoreMapper;
import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.app.service.StoreService;
import com.github.alexeysol.common.model.dto.CreateStoreDto;
import com.github.alexeysol.common.model.dto.StoreDto;
import com.github.alexeysol.common.model.dto.UpdateStoreDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/store", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class StoreController {
    private static final String STORE = "Store";

    private final StoreService storeService;

    private final StoreMapper storeMapper = StoreMapper.INSTANCE;

    @GetMapping
    public Page<StoreDto> getStores(
        @RequestParam Optional<String> city,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Store> storePage;

        if (city.isPresent()) {
            storePage = getAllStoresByCity(city.get(), pageable);
        } else {
            storePage = getAllStores(pageable);
        }

        return storeMapper.map(storePage, pageable);
    }

    private Page<Store> getAllStoresByCity(String city, Pageable pageable) {
        return storeService.findAllStoresByCity(city, pageable);
    }

    private Page<Store> getAllStores(Pageable pageable) {
        return storeService.findAllStores(pageable);
    }

    @GetMapping("/{id}")
    public StoreDto getStoreById(@PathVariable long id) {
        var store = storeService.findStoreById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, STORE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return storeMapper.map(store);
    }

    @PostMapping
    public StoreDto createStore(@RequestBody @Valid CreateStoreDto dto) {
        Store store = storeMapper.map(dto);
        storeService.saveStore(store);
        return storeMapper.map(store);
    }

    @PatchMapping("/{id}")
    public StoreDto updateStoreById(@PathVariable long id, @RequestBody @Valid UpdateStoreDto dto) {
        var store = storeService.findStoreById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, STORE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Store updatedStore = storeMapper.map(dto, store);
        storeService.saveStore(updatedStore);
        return storeMapper.map(updatedStore);
    }
}
