package com.webflux.products.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        @Size(max = 10)
        String code,
        @NotBlank
        @Size(max = 100)
        String name,
        @Size(max = 200)
        String description,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,
        @NotBlank
        @Size(max = 100)
        String category,
        @NotNull
        Boolean state
) {
}
