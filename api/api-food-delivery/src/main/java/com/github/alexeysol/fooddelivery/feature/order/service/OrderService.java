package com.github.alexeysol.fooddelivery.feature.order.service;

import com.github.alexeysol.fooddelivery.feature.order.constant.OrderConstant;
import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.fooddelivery.feature.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAllOrdersByUserId(long userId) {
        return orderRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    public Optional<Order> findOrderById(long id) {
        return orderRepository.findById(id);
    }

    public boolean hasActiveOrderByUserIdAndPlaceId(long userId, long placeId) {
        return orderRepository.existsByUserIdAndPlaceIdAndStatusIn(userId, placeId, OrderConstant.ACTIVE_STATUSES);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
