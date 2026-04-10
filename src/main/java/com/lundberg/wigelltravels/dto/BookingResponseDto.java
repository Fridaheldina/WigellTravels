package com.lundberg.wigelltravels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingResponseDto(
        @NotNull Long id,
        @NotBlank String destination,
        @NotBlank String hotel,
        @NotNull int weeks,
        @NotNull double totalPriceSek,
        @NotNull double totalPricePln
) {
}
