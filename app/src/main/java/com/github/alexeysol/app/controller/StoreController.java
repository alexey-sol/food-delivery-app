package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.StoreMapper;
import com.github.alexeysol.app.model.PagingCriteria;
import com.github.alexeysol.app.model.dto.CreateStoreDto;
import com.github.alexeysol.app.model.dto.StoreDto;
import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.app.service.StoreService;
import com.github.alexeysol.app.util.PageableUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Page<StoreDto> getStores(@RequestParam Optional<String> city, @RequestParam Optional<String> paging) {
        var pageable = paging.isPresent()
            ? PageableUtil.convert(paging.get())
            : PageableUtil.convert(new PagingCriteria());

        Page<Store> storePage;

        if (city.isPresent()) {
            storePage = getAllStoresByCity(city.get(), pageable);
        } else {
            storePage = getAllStores(pageable);
        }

        return storeMapper.map(storePage, pageable);
    }

    public Page<Store> getAllStoresByCity(String city, Pageable pageable) {
        return storeService.findAllStoresByCity(city, pageable);
    }

    public Page<Store> getAllStores(Pageable pageable) {
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StoreDto createStore(@RequestBody @Valid CreateStoreDto dto) {
        Store store = storeMapper.map(dto);
        storeService.saveStore(store);
        return storeMapper.map(store);
    }
}
