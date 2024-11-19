package org.betfair.christmas_logistic.controller.dto;

import jakarta.validation.constraints.*;

public record DestinationCreateDto(
        @Null
        Long id,
        @NotBlank
        String name,
        @NotNull
        @Min(1)
        @Max(99999)
        Integer distance
) {
}
