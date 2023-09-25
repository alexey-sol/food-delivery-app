package com.github.alexeysol.fooddelivery.feature.user.repository;

import com.github.alexeysol.fooddelivery.feature.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);
}
