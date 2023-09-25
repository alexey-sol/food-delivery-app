package com.github.alexeysol.fooddelivery.feature.city.repository;

import com.github.alexeysol.fooddelivery.feature.city.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
