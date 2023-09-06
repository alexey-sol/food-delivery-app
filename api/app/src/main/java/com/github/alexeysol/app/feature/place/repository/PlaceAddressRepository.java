package com.github.alexeysol.app.feature.place.repository;

import com.github.alexeysol.app.feature.place.model.entity.PlaceAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAddressRepository extends JpaRepository<PlaceAddress, Long> {
}
