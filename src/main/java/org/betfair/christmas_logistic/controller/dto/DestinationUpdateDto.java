package org.betfair.christmas_logistic.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DestinationUpdateDto(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotNull
        @Min(1)
        @Max(99999)
        Integer distance
) {
}
