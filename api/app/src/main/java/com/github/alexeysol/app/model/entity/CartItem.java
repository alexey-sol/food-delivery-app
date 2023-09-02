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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private int quantity = INITIAL_QUANTITY;


//    @ManyToMany(cascade = { CascadeType.MERGE })
//    @JoinTable(
//        name = "cart_item_product",
//        joinColumns = {
//            @JoinColumn(name = "product_id", referencedColumnName = "id")
//        },
//        inverseJoinColumns = {
//            @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
//        }
//    )
    // private Set<Product> products

    // TODO ideally make it many-to-many
//    @OneToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    // https://stackoverflow.com/questions/66853058/hibernate-in-a-one-to-many-relationship-a-child-loses-references-to-the-paren
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

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
