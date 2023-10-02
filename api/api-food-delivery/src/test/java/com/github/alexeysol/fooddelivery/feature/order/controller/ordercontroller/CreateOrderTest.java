package com.github.alexeysol.fooddelivery.feature.order.controller.ordercontroller;

import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.User;
import com.github.alexeysol.common.feature.order.model.dto.CreateOrderDto;
import com.github.alexeysol.common.feature.order.model.dto.CreateOrderItemDto;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.shared.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class CreateOrderTest extends BaseOrderControllerTest {
    private static final CreateOrderDto CREATE_ORDER_DTO = CreateOrderDto.builder()
        .placeId(1L)
        .orderItems(List.of(CreateOrderItemDto.builder()
            .productId(1L)
            .quantity(10)
            .build()))
        .build();

    public CreateOrderTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenCreateOrder_thenReturnsOrderDto() {
        var order = Order.builder().orderItems(new HashSet<>()).build();
        var orderDto = OrderDto.builder().build();

        when(orderService.hasActiveOrderByUserIdAndPlaceId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.of(new User()));
        when(orderMapper.map(Mockito.any(CreateOrderDto.class), Mockito.any(User.class), Mockito.any(Order.class))).thenReturn(order);
        when(orderMapper.map(Mockito.any(Order.class))).thenReturn(orderDto);

        mockMvc.perform(TestUtil.mockPostRequest(getUserOrderUri(1), CREATE_ORDER_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(orderDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenInvalidDto_whenCreateOrder_thenThrowsMethodArgumentNotValidException() {
        var createOrderDto = CreateOrderDto.builder().build();

        mockMvc.perform(TestUtil.mockPostRequest(getUserOrderUri(1), createOrderDto))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @SneakyThrows
    public void givenOrderIsAlreadyExists_whenCreateOrder_thenThrowsResponseStatusException() {
        when(orderService.hasActiveOrderByUserIdAndPlaceId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

        mockMvc.perform(TestUtil.mockPostRequest(getUserOrderUri(1), CREATE_ORDER_DTO))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    @SneakyThrows
    public void givenUserDoesntExist_whenCreateOrder_thenThrowsResponseStatusException() {
        when(orderService.hasActiveOrderByUserIdAndPlaceId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
        when(userService.findUserById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPostRequest(getUserOrderUri(1), CREATE_ORDER_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> {
                Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException);
            });
    }
}
