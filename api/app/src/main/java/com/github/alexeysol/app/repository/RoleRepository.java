package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
