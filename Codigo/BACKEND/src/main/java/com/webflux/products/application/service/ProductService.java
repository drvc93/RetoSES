package com.webflux.products.application.service;

import com.webflux.products.domain.model.Product;
import com.webflux.products.domain.port.in.CreateProductUseCase;
import com.webflux.products.domain.port.in.DeleteProductUseCase;
import com.webflux.products.domain.port.in.GetProductUseCase;
import com.webflux.products.domain.port.in.ListProductsUseCase;
import com.webflux.products.domain.port.in.UpdateProductUseCase;
import com.webflux.products.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ProductService implements
        CreateProductUseCase,
        GetProductUseCase,
        ListProductsUseCase,
        UpdateProductUseCase,
        DeleteProductUseCase {

    private final ProductRepositoryPort repositoryPort;

    public ProductService(ProductRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Mono<Product> create(Product product) {
        LocalDateTime now = LocalDateTime.now();
        Product newProduct = new Product(
                null,
                product.code(),
                product.name(),
                product.description(),
                product.price(),
                product.category(),
                now,
                null,
                product.state() != null ? product.state() : Boolean.TRUE
        );
        return repositoryPort.save(newProduct);
    }

    @Override
    public Mono<Product> getById(Long id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Flux<Product> listAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Mono<Product> update(Long id, Product product) {
        return repositoryPort.findById(id)
                .flatMap(existing -> repositoryPort.save(new Product(
                        existing.id(),
                        product.code(),
                        product.name(),
                        product.description(),
                        product.price(),
                        product.category(),
                        existing.regDate(),
                        LocalDateTime.now(),
                        product.state()
                )));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repositoryPort.deleteById(id);
    }
}
