package com.github.alexeysol.app.service;

import com.github.alexeysol.app.constant.OrderConstant;
import com.github.alexeysol.app.model.entity.Order;
import com.github.alexeysol.app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAllOrdersByUserId(long userId) {
        return orderRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    // boolean existsByUserIdAndPlaceIdAndStatusIn(long userId, long placeId, List<OrderStatus> statuses);
    public boolean hasActiveOrderByUserIdAndPlaceId(long userId, long placeId) {
        return orderRepository.existsByUserIdAndPlaceIdAndStatusIn(userId, placeId, OrderConstant.ACTIVE_STATUSES);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
