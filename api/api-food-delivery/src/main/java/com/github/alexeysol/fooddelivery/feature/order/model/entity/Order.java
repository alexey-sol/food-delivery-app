package com.github.alexeysol.fooddelivery.feature.order.model.entity;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.User;
import com.github.alexeysol.common.shared.model.OrderStatus;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PROCESSING;

    @Cascade({ DELETE_ORPHAN })
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
