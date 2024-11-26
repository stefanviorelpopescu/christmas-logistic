package org.betfair.christmas_logistic.controller;

import lombok.RequiredArgsConstructor;
import org.betfair.christmas_logistic.controller.dto.DestinationCreateDto;
import org.betfair.christmas_logistic.controller.dto.DestinationDto;
import org.betfair.christmas_logistic.controller.dto.DestinationUpdateDto;
import org.betfair.christmas_logistic.controller.exception.BadDestinationRequestException;
import org.betfair.christmas_logistic.service.DestinationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping
    public DestinationDto createDestination(@RequestBody @Validated DestinationCreateDto destinationToCreate) {
        return destinationService.createDestination(destinationToCreate);
    }

    @PutMapping
    public DestinationDto updateDestination(@RequestBody @Validated DestinationUpdateDto destinationToUpdate) throws BadDestinationRequestException {
        return destinationService.updateDestination(destinationToUpdate);
    }

    @DeleteMapping("/{destinationId}")
    public void deleteDestination(@PathVariable Long destinationId) {
        destinationService.deleteDestination(destinationId);
    }

    @GetMapping("/{destinationId}")
    public DestinationDto getDestination(@PathVariable Long destinationId) throws BadDestinationRequestException {
        return destinationService.getDestination(destinationId);
    }

    @GetMapping
    public List<DestinationDto> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

}
