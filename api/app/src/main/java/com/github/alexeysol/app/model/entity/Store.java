package com.github.alexeysol.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private StoreAddress address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Cart> carts = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
