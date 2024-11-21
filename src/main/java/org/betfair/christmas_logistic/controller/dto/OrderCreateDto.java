package org.betfair.christmas_logistic.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderCreateDto(
        String destinationName,
        String deliveryDate,
        Long lastUpdated
) {}
