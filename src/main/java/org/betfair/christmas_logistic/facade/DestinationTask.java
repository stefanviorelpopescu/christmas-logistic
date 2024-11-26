package org.betfair.christmas_logistic.facade;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.betfair.christmas_logistic.dao.Destination;
import org.betfair.christmas_logistic.dao.Order;

import java.util.List;
import java.util.Map;

@Slf4j
public class DestinationTask implements Runnable{

    private Destination destination;
    private List<Order> orders;

    public DestinationTask(Map.Entry<Destination, List<Order>> destinationAndOrders) {
        this.destination = destinationAndOrders.getKey();
        this.orders = destinationAndOrders.getValue();
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(5_000);
        log.info("Delivered to {}", destination.getName());
    }
}
