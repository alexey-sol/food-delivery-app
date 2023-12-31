package com.github.alexeysol.fooddelivery.feature.locality.repository;

import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
}
