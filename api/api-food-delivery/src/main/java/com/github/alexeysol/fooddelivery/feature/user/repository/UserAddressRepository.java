package com.github.alexeysol.fooddelivery.feature.user.repository;

import com.github.alexeysol.fooddelivery.feature.user.model.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
