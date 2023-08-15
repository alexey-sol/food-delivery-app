package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.StoreMapper;
import com.github.alexeysol.app.model.dto.CreateStoreDto;
import com.github.alexeysol.app.model.dto.StoreDto;
import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.app.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping(value = "/store", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    private final StoreMapper storeMapper = StoreMapper.INSTANCE;

    @GetMapping("/all/{city}")
    public Set<StoreDto> getAllStoresByCity(@PathVariable String city) {
        var stores = storeService.findAllStoresByCity(city);
        return storeMapper.map(stores);
    }

    @GetMapping("/{id}")
    public StoreDto getStoreById(@PathVariable long id) {
        var store = storeService.findStoreById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, "Product", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return storeMapper.map(store);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StoreDto createStore(@RequestBody @Valid CreateStoreDto dto) {
        Store store = storeMapper.map(dto);
        storeService.saveStore(store);
        return storeMapper.map(store);
    }
}
