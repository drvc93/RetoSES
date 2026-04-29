package com.webflux.products.infrastructure.adapters.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
}
