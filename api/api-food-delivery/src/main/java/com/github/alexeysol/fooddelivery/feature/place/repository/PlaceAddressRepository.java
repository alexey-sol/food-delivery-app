package com.github.alexeysol.fooddelivery.feature.place.repository;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.PlaceAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAddressRepository extends JpaRepository<PlaceAddress, Long> {
}
