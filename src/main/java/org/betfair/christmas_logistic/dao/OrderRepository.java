package org.betfair.christmas_logistic.dao;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.betfair.christmas_logistic.dao.OrderStatus.*;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDestination_NameContainingIgnoreCaseAndDeliveryDate(String destinationName, String deliveryDate);
    List<Order> findAllByDeliveryDate(String deliveryDate);

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Order> findAllById(@NonNull Iterable<Long> ids);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    default void markAsDelivering(List<Long> orderIds) {
        List<Order> orders = findAllById(orderIds);
        orders.forEach(order -> changeOrderStatus(order, DELIVERING));
        saveAll(orders);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    default int markAsDelivered(List<Long> orderIds) {
        List<Order> orders = findAllById(orderIds);
        int count = orders.stream()
                .mapToInt(order -> changeOrderStatus(order, DELIVERED))
                .sum();
        saveAll(orders);
        return count;
    }

    default int changeOrderStatus(Order order, OrderStatus newStatus) {
        if (statusTransitionRules.get(newStatus).contains(order.getStatus())) {
            order.setStatus(newStatus);
            return 1;
        }
        return 0;
    }

    Map<OrderStatus, List<OrderStatus>> statusTransitionRules = Map.of(
            NEW, Collections.emptyList(),
            DELIVERING, List.of(NEW),
            DELIVERED, List.of(DELIVERING),
            CANCELED, List.of(NEW, DELIVERING)
    );
}
