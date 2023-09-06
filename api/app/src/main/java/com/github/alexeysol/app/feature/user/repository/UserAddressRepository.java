package com.github.alexeysol.app.feature.user.repository;

import com.github.alexeysol.app.feature.user.model.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
