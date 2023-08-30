package com.github.alexeysol.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String phone;

    @Column
    private String username;

    @Column
    private String password;

    // TODO really need this?
//    @Transient
//    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

//    @Column(name = "full_name")

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private UserAddress address;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "cart_id", referencedColumnName = "id")
//    private Cart cart;
    @OneToMany(mappedBy = "user")
    private Set<Cart> carts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
