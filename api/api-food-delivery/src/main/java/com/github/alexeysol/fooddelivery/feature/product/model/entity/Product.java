package com.github.alexeysol.fooddelivery.feature.product.model.entity;

import com.github.alexeysol.fooddelivery.feature.cart.model.entity.CartItem;
import com.github.alexeysol.fooddelivery.feature.order.model.entity.OrderItem;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = {
    @Index(columnList = "name"),
    @Index(columnList = "place_id")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private String name;

    private String description;

    private int calories;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_category",
        joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id")
        }
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
