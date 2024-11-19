package org.betfair.christmas_logistic.converter;

import org.betfair.christmas_logistic.controller.dto.DestinationCreateDto;
import org.betfair.christmas_logistic.controller.dto.DestinationDto;
import org.betfair.christmas_logistic.controller.dto.DestinationUpdateDto;
import org.betfair.christmas_logistic.dao.Destination;

public class DestinationConverter {

    public static Destination createDtoToEntity(DestinationCreateDto destinationCreateDto) {
        return Destination.builder()
                .id(destinationCreateDto.id())
                .name(destinationCreateDto.name())
                .distance(destinationCreateDto.distance())
                .build();
    }
    public static Destination updateDtoToEntity(DestinationUpdateDto destinationUpdateDto) {
        return Destination.builder()
                .id(destinationUpdateDto.id())
                .name(destinationUpdateDto.name())
                .distance(destinationUpdateDto.distance())
                .build();
    }

    public static DestinationDto entityToDto(Destination destination) {
        return new DestinationDto(
                destination.getId(),
                destination.getName(),
                destination.getDistance()
        );
    }

}
