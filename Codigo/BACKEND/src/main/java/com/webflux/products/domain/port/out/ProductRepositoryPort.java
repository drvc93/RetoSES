package com.webflux.products.domain.port.out;

import com.webflux.products.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {
    Mono<Product> save(Product product);

    Mono<Product> findById(Long id);

    Flux<Product> findAll();

    Mono<Void> deleteById(Long id);
}
