package com.webflux.products.infrastructure.adapters.in.web;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Producto con id " + id + " no encontrado");
    }
}
