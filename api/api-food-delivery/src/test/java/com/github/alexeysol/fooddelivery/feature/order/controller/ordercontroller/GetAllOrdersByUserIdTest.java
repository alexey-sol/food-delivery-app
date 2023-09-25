package com.github.alexeysol.fooddelivery.feature.order.controller.ordercontroller;

import com.github.alexeysol.fooddelivery.feature.order.model.entity.Order;
import com.github.alexeysol.common.feature.order.model.dto.OrderDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

public class GetAllOrdersByUserIdTest extends BaseOrderControllerTest {
    public GetAllOrdersByUserIdTest(@Autowired MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    @SneakyThrows
    public void ordersExist_whenGetAllOrdersByUserId_thenReturnsOrderDtoList() {
        var orders = List.of(new Order(), new Order());

        var orderDtoList = List.of(OrderDto.builder().build(), OrderDto.builder().build());

        when(orderService.findAllOrdersByUserId(Mockito.anyLong())).thenReturn(orders);
        when(orderMapper.map(Mockito.anyList())).thenReturn(orderDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl())
                .param("userId", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
            .andExpect(result -> {
                var expected = objectMapper.writeValueAsString(orderDtoList);
                var actual = result.getResponse().getContentAsString();
                Assertions.assertEquals(expected, actual);
            });
    }
}
