package com.github.alexeysol.fooddelivery.feature.order.controller.ordercontroller;

import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import com.github.alexeysol.common.feature.order.model.dto.UpdateOrderDto;
import com.github.alexeysol.common.shared.model.OrderStatus;
import com.github.alexeysol.common.shared.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UpdateOrderByIdTest extends BaseOrderControllerTest {
    private static final UpdateOrderDto UPDATE_ORDER_DTO = UpdateOrderDto.builder()
        .status(OrderStatus.CANCELLED)
        .build();

    public UpdateOrderByIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void givenValidDto_whenUpdateOrderById_thenReturnsOrderDto() {
        var order = Order.builder().orderItems(new HashSet<>()).build();
        var orderDto = OrderDto.builder().build();

        when(orderService.findOrderById(Mockito.anyLong())).thenReturn(Optional.of(order));
        when(orderMapper.map(Mockito.any(UpdateOrderDto.class), Mockito.any(Order.class))).thenReturn(order);
        when(orderMapper.map(Mockito.any(Order.class))).thenReturn(orderDto);

        mockMvc.perform(TestUtil.mockPatchRequest(getOrderUri(1), UPDATE_ORDER_DTO))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(orderDto);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }

    @Test
    @SneakyThrows
    public void givenOrderDoesntExist_whenUpdateOrderById_thenThrowsResponseStatusException() {
        when(orderService.findOrderById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(TestUtil.mockPatchRequest(getOrderUri(0), UPDATE_ORDER_DTO))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }
}
