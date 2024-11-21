package org.betfair.christmas_logistic.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    Destination destination;

    String deliveryDate;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    Long lastUpdated;

}
