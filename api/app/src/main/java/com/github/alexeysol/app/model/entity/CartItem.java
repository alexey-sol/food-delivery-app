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
@Table(name = "cart_item")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private final static int QUANTITY_UPDATE_STEP = 1;
    private final static int INITIAL_QUANTITY = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq")
    @SequenceGenerator(name = "cart_item_seq", sequenceName = "cart_item_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity = INITIAL_QUANTITY;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    public int incrementQuantity() {
        return incrementQuantity(QUANTITY_UPDATE_STEP);
    }

    public int incrementQuantity(int count) {
        this.quantity += count;
        normalizeQuantityIfNeeded();
        return this.quantity;
    }

    public int decrementQuantity() {
        return decrementQuantity(QUANTITY_UPDATE_STEP);
    }

    public int decrementQuantity(int count) {
        this.quantity -= count;
        normalizeQuantityIfNeeded();
        return this.quantity;
    }

    private void normalizeQuantityIfNeeded() {
        if (quantity < INITIAL_QUANTITY) {
            quantity = INITIAL_QUANTITY;
        }
    }

    public boolean isIdle() {
        return quantity <= INITIAL_QUANTITY;
    }
}
