package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends PagingAndSortingRepository<Place, Long>, JpaRepository<Place, Long> {
    Page<Place> findAllByAddressCityId(long cityId, Pageable pageable);
}
