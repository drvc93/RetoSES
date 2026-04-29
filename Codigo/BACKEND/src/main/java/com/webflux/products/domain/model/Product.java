package com.webflux.products.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Product(
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
