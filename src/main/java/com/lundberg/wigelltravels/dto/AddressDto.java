package com.lundberg.wigelltravels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(
        @NotNull Long id,
        @NotBlank String street,
        @NotBlank String city
        ) {
}
