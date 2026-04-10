package com.lundberg.wigelltravels.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerCreateDto(
        @NotBlank String name,
        @NotBlank String username,
        @NotBlank String password
) {
}
