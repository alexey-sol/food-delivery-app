package com.github.alexeysol.app.model.entity;

import com.github.alexeysol.common.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.DELETE_ORPHAN;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private final static List<OrderStatus> ACTIVE_STATUSES = List.of(OrderStatus.DELIVERING, OrderStatus.PROCESSING);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
    // TODO added for convenience only, to make it easier to render orders page (with brief store info)
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;
//    @Column(name = "type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private OrderType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PROCESSING;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    @Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
    @Cascade({ DELETE_ORPHAN })
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // TODO Cascade PERSIST - when saving order, order items get saved as well automatically
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    //     CANCELLED,
    //    COMPLETED,
    //    DELIVERING,
    //    FAILED,
    //    PROCESSING,
    public boolean isActive() {
        return ACTIVE_STATUSES.contains(status);
    }
}
