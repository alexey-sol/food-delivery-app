package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.PlaceAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAddressRepository extends JpaRepository<PlaceAddress, Long> {
}
