package org.betfair.christmas_logistic.converter;

import org.betfair.christmas_logistic.controller.dto.OrderDto;
import org.betfair.christmas_logistic.dao.Order;

import java.util.List;

public class OrderConverter {

    public static OrderDto entityToDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getDestination().getName(),
                order.getDeliveryDate(),
                order.getStatus(),
                order.getLastUpdated()
        );
    }

    public static List<OrderDto> entityListToDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderConverter::entityToDto)
                .toList();
    }
}
