package com.github.alexeysol.app.model.entity;

import com.github.alexeysol.common.model.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    private UserRole name; // TODO enum?

    @Transient
//    @ManyToMany(mappedBy = "roles")
    @ManyToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return String.valueOf(name); // TODO ok?
    }
}
