package org.betfair.christmas_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDestination_NameContainingIgnoreCaseAndDeliveryDate(String destinationName, String deliveryDate);
    List<Order> findAllByDeliveryDate(String deliveryDate);

}
