package org.betfair.christmas_logistic.service;

import lombok.RequiredArgsConstructor;
import org.betfair.christmas_logistic.controller.dto.DestinationCreateDto;
import org.betfair.christmas_logistic.controller.dto.DestinationDto;
import org.betfair.christmas_logistic.controller.dto.DestinationUpdateDto;
import org.betfair.christmas_logistic.controller.exception.BadDestinationRequestException;
import org.betfair.christmas_logistic.converter.DestinationConverter;
import org.betfair.christmas_logistic.dao.Destination;
import org.betfair.christmas_logistic.dao.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.betfair.christmas_logistic.converter.DestinationConverter.*;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public List<DestinationDto> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(DestinationConverter::entityToDto)
                .toList();
    }

    public DestinationDto getDestination(Long destinationId) throws BadDestinationRequestException {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new BadDestinationRequestException(String.format("Destination not found for id %d", destinationId)));
        return entityToDto(destination);
    }

    public DestinationDto createDestination(DestinationCreateDto destinationToCreate) {
        Destination destination = createDtoToEntity(destinationToCreate);
        Destination savedDestination = destinationRepository.save(destination);
        return entityToDto(savedDestination);
    }

    public DestinationDto updateDestination(DestinationUpdateDto destinationToUpdate) throws BadDestinationRequestException {
        Long destinationId = destinationToUpdate.id();
        if (!destinationRepository.existsById(destinationId)) {
            throw new BadDestinationRequestException(String.format("Destination not found for id %d", destinationId));
        }
        if (!destinationRepository.findAllByName(destinationToUpdate.name()).isEmpty()) {
            throw new BadDestinationRequestException("Destination with the same name already exists");
        }

        Destination destinationToSave = updateDtoToEntity(destinationToUpdate);
        Destination savedDestination = destinationRepository.save(destinationToSave);
        return entityToDto(savedDestination);
    }

    public void deleteDestination(Long destinationId) {
        destinationRepository.findById(destinationId)
                .ifPresent(destinationRepository::delete);
    }
}
