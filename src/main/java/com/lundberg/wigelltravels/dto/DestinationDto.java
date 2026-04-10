package com.lundberg.wigelltravels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DestinationDto(
        @NotNull Long id,
        @NotBlank String city,
        @NotBlank String country,
        @NotBlank String hotelName,
        @NotNull double pricePerWeek
) {
}
