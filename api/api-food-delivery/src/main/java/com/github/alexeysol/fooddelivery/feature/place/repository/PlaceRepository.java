package com.github.alexeysol.fooddelivery.feature.place.repository;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends PagingAndSortingRepository<Place, Long>, JpaRepository<Place, Long> {
    @Query("SELECT p FROM Place p JOIN FETCH p.address")
    Page<Place> findAllByAddressCityId(long cityId, Pageable pageable);

    @NonNull
    @Query("SELECT p FROM Place p JOIN FETCH p.address")
    Page<Place> findAll(@NonNull Pageable pageable);
}
