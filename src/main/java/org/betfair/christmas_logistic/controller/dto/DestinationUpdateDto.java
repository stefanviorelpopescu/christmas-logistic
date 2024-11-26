package org.betfair.christmas_logistic.controller.dto;

import jakarta.validation.constraints.*;

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

        @AssertTrue(message = "name invalid")
        public boolean isNameLongerThanDistance() {
                return name.length() < distance;
        }

}
