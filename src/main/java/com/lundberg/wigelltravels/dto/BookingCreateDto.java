package com.lundberg.wigelltravels.dto;

import jakarta.validation.constraints.Min;
import org.antlr.v4.runtime.misc.NotNull;
import java.time.LocalDate;

public record BookingCreateDto(
    @NotNull Long customerId,
    @NotNull Long destinationId,
    @Min(1) int weeks,
    @NotNull LocalDate startDate
    ) {
}
