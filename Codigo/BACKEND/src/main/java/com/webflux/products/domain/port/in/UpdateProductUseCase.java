package com.webflux.products.domain.port.in;

import com.webflux.products.domain.model.Product;
import reactor.core.publisher.Mono;

public interface UpdateProductUseCase {
    Mono<Product> update(Long id, Product product);
}
