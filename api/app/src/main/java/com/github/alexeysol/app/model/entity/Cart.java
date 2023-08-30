package com.github.alexeysol.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

//    @OneToOne(mappedBy = "cart")
//    private User user;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @OneToMany(mappedBy = "cart")
//    private Set<CartItem> cartItems = new HashSet<>();
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    // TODO price pos or neg
    public long addPrice(long price) {
        totalPrice += price;
        normalizeTotalPriceIfNeeded();
        return totalPrice;
    }

    public long subtractPrice(long price) {
        totalPrice -= price;
        normalizeTotalPriceIfNeeded();
        return totalPrice;
    }

    private void normalizeTotalPriceIfNeeded() {
        if (totalPrice < 0) { // TODO const INITIAL_PRICE
            totalPrice = 0;
        }
    }
}
