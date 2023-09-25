package com.github.alexeysol.fooddelivery.feature.order.repository;

import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.common.shared.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByIdDesc(long userId);

    boolean existsByUserIdAndPlaceIdAndStatusIn(long userId, long placeId, List<OrderStatus> statuses);
}
