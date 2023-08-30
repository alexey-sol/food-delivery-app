package com.github.alexeysol.app.repository;

import com.github.alexeysol.app.model.entity.Order;
import com.github.alexeysol.common.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByIdDesc(long userId);

    boolean existsByUserIdAndStoreIdAndStatusIn(long userId, long storeId, List<OrderStatus> statuses);
}
