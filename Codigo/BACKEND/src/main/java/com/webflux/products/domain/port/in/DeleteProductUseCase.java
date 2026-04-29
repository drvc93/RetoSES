package com.webflux.products.domain.port.in;

import reactor.core.publisher.Mono;

public interface DeleteProductUseCase {
    Mono<Void> delete(Long id);
}
