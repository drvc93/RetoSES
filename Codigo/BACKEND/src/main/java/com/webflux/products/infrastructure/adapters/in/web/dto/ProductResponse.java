package com.webflux.products.infrastructure.adapters.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String code,
        String name,
        String description,
        BigDecimal price,
        String category,
        LocalDateTime regDate,
        LocalDateTime modDate,
        Boolean state
) {
}
