package com.webflux.products.domain.port.in;

import com.webflux.products.domain.model.Product;
import reactor.core.publisher.Mono;

public interface CreateProductUseCase {
    Mono<Product> create(Product product);
}
