package org.betfair.christmas_logistic.facade;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.betfair.christmas_logistic.dao.Destination;
import org.betfair.christmas_logistic.dao.Order;
import org.betfair.christmas_logistic.dao.OrderRepository;
import org.betfair.christmas_logistic.dao.OrderStatus;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class DestinationTask implements Runnable{

    private Destination destination;
    private List<Order> orders;
    private OrderRepository orderRepository;

    @SneakyThrows
    @Override
    public void run() {
        log.info("Starting deliveries to {} on {} for {} km.", destination.getName(), Thread.currentThread().getName(), destination.getDistance());
        orders.forEach(order -> order.setStatus(OrderStatus.DELIVERING));
        orderRepository.saveAll(orders);

        Thread.sleep(destination.getDistance() * 1000);

        orders.forEach(order -> order.setStatus(OrderStatus.DELIVERED));
        orderRepository.saveAll(orders);

        log.info("Delivered to {}", destination.getName());
    }
}
