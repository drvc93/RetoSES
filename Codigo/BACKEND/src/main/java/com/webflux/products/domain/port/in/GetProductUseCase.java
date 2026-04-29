package com.webflux.products.domain.port.in;

import com.webflux.products.domain.model.Product;
import reactor.core.publisher.Mono;

public interface GetProductUseCase {
    Mono<Product> getById(Long id);
}
