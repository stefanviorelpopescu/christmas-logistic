package org.betfair.christmas_logistic.converter;

import org.betfair.christmas_logistic.controller.dto.OrderCreateDto;
import org.betfair.christmas_logistic.controller.dto.OrderDto;
import org.betfair.christmas_logistic.dao.Destination;
import org.betfair.christmas_logistic.dao.Order;
import org.betfair.christmas_logistic.dao.OrderStatus;

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

    public static Order createDtoToEntity(OrderCreateDto orderCreateDto, Destination destination) {
        return Order.builder()
                .deliveryDate(orderCreateDto.deliveryDate())
                .destination(destination)
                .status(OrderStatus.NEW)
                .lastUpdated(System.currentTimeMillis())
                .build();
    }
}
