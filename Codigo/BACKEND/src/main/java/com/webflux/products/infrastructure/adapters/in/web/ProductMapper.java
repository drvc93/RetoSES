package com.webflux.products.infrastructure.adapters.in.web;

import com.webflux.products.domain.model.Product;
import com.webflux.products.infrastructure.adapters.in.web.dto.ProductRequest;
import com.webflux.products.infrastructure.adapters.in.web.dto.ProductResponse;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static Product toDomain(ProductRequest request) {
        return new Product(
                null,
                request.code(),
                request.name(),
                request.description(),
                request.price(),
                request.category(),
                null,
                null,
                request.state()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.id(),
                product.code(),
                product.name(),
                product.description(),
                product.price(),
                product.category(),
                product.regDate(),
                product.modDate(),
                product.state()
        );
    }
}
