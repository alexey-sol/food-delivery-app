package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.StoreAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreAddressRepository extends JpaRepository<StoreAddress, Long> {
}
