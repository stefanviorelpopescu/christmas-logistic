package org.betfair.christmas_logistic.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "destinations")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    Integer distance;

//    @OneToMany(fetch = FetchType.LAZY)
//    List<Order> orders;
}
