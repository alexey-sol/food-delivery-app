package com.github.alexeysol.fooddelivery.feature.user.repository;

import com.github.alexeysol.fooddelivery.feature.user.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
