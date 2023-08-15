package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByAddressCityIgnoreCase(String city);
}
