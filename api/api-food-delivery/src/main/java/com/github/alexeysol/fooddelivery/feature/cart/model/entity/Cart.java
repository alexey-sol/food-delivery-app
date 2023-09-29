package com.github.alexeysol.fooddelivery.feature.cart.model.entity;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

import static org.hibernate.annotations.CascadeType.DELETE_ORPHAN;

@Entity
@Table(indexes = {
    @Index(columnList = "user_id"),
    @Index(columnList = "user_id, place_id")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private static final long INITIAL_TOTAL_PRICE = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @Column(name = "total_price", nullable = false)
    private long totalPrice = INITIAL_TOTAL_PRICE;

    @Cascade({ DELETE_ORPHAN })
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    public void incrementTotalPrice(long amount) {
        totalPrice += amount;
        normalizeTotalPriceIfNeeded();
    }

    public void decrementTotalPrice(long amount) {
        totalPrice -= amount;
        normalizeTotalPriceIfNeeded();
    }

    private void normalizeTotalPriceIfNeeded() {
        if (totalPrice < INITIAL_TOTAL_PRICE) {
            totalPrice = INITIAL_TOTAL_PRICE;
        }
    }

    public boolean isIdle() {
        return cartItems.isEmpty();
    }
}
