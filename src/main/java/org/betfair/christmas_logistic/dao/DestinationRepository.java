package org.betfair.christmas_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    Optional<Destination> findByName(String name);
    List<Destination> findAllByNameIn(List<String> destinationNames);
}
