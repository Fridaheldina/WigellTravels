package com.lundberg.wigelltravels.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerResponseDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String username
) {
}
