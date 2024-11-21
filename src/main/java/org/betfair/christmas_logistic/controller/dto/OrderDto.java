package org.betfair.christmas_logistic.controller.dto;

import org.betfair.christmas_logistic.dao.OrderStatus;

public record OrderDto(
        Long id,
        String destinationName,
        String deliveryDate,
        OrderStatus status,
        Long lastUpdated
) {}
