package com.github.alexeysol.app.service;

import com.github.alexeysol.app.constant.OrderConstant;
import com.github.alexeysol.app.model.entity.Order;
import com.github.alexeysol.app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Set<Order> findAllOrdersByUserId(long userId) {
        return new HashSet<>(orderRepository.findAllByUserIdOrderByIdDesc(userId));
    }

    // boolean existsByUserIdAndStoreIdAndStatusIn(long userId, long storeId, List<OrderStatus> statuses);
    public boolean hasActiveOrderByUserIdAndStoreId(long userId, long storeId) {
        return orderRepository.existsByUserIdAndStoreIdAndStatusIn(userId, storeId, OrderConstant.ACTIVE_STATUSES);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
