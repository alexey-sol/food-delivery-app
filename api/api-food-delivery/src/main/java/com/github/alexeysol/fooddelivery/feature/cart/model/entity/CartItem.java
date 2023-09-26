package com.github.alexeysol.fooddelivery.feature.cart.model.entity;

import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY)
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

    public void incrementQuantity(int amount) {
        this.quantity += amount;
        normalizeQuantityIfNeeded();
    }

    public void decrementQuantity(int amount) {
        this.quantity -= amount;
        normalizeQuantityIfNeeded();
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
