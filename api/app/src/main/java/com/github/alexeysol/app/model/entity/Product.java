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
@Table(indexes = {
    @Index(columnList = "name"),
    @Index(columnList = "store_id")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private int calories;

    // TODO check if quantity = 0 then need to cook it up?
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private long price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // TODO maybe it should be many-to-one
    @ManyToMany(cascade = { CascadeType.MERGE })
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

//    @ManyToMany(mappedBy = "product")
    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
