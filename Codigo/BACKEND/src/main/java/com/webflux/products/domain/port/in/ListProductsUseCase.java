package com.webflux.products.domain.port.in;

import com.webflux.products.domain.model.Product;
import reactor.core.publisher.Flux;

public interface ListProductsUseCase {
    Flux<Product> listAll();
}
