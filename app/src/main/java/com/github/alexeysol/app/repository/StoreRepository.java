package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>, JpaRepository<Store, Long> {
    Page<Store> findAllByAddressCityIgnoreCase(String city, Pageable pageable);
}
