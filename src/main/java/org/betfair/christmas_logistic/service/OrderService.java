package org.betfair.christmas_logistic.service;

import lombok.RequiredArgsConstructor;
import org.betfair.christmas_logistic.controller.dto.OrderCreateDto;
import org.betfair.christmas_logistic.controller.dto.OrderDto;
import org.betfair.christmas_logistic.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.betfair.christmas_logistic.converter.OrderConverter.entityListToDtoList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;
    private final CompanyInfo companyInfo;

    public void cancelOrders(List<Long> orderIds) {
        List<Order> ordersToCancel = orderRepository.findAllById(orderIds);
        for (Order order : ordersToCancel) {
            if (!order.getStatus().equals(OrderStatus.DELIVERED)
                    && !order.getStatus().equals(OrderStatus.CANCELED)) {
                order.setStatus(OrderStatus.CANCELED);
            }
        }
        orderRepository.saveAll(ordersToCancel);
    }

    public List<OrderDto> findOrders(String deliveryDate, String destinationName) {
        if (deliveryDate == null || deliveryDate.isBlank()) {
            deliveryDate = companyInfo.getCurrentDateString();
        }
        if (destinationName == null || destinationName.isBlank()) {
            destinationName = "";
        }

        List<Order> foundOrders = orderRepository.findAllByDestination_NameContainingAndDeliveryDate(destinationName, deliveryDate);
        return entityListToDtoList(foundOrders);
    }

    public List<OrderDto> createOrders(List<OrderCreateDto> ordersToCreate) {

        List<String> names = ordersToCreate.stream()
                .map(OrderCreateDto::destinationName)
                .toList();
//        destinationRepository.findAllName

        return null;
    }
}
