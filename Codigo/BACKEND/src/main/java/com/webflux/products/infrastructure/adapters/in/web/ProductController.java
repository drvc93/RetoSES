package com.webflux.products.infrastructure.adapters.in.web;

import com.webflux.products.domain.model.Product;
import com.webflux.products.domain.port.in.CreateProductUseCase;
import com.webflux.products.domain.port.in.DeleteProductUseCase;
import com.webflux.products.domain.port.in.GetProductUseCase;
import com.webflux.products.domain.port.in.ListProductsUseCase;
import com.webflux.products.domain.port.in.UpdateProductUseCase;
import com.webflux.products.infrastructure.adapters.in.web.dto.ProductRequest;
import com.webflux.products.infrastructure.adapters.in.web.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductController(
            CreateProductUseCase createProductUseCase,
            GetProductUseCase getProductUseCase,
            ListProductsUseCase listProductsUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product = ProductMapper.toDomain(request);
        return createProductUseCase.create(product).map(ProductMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ProductResponse> getById(@PathVariable Long id) {
        return getProductUseCase.getById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(id)))
                .map(ProductMapper::toResponse);
    }

    @GetMapping
    public Flux<ProductResponse> listAll() {
        return listProductsUseCase.listAll().map(ProductMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product product = ProductMapper.toDomain(request);
        return updateProductUseCase.update(id, product)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(id)))
                .map(ProductMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return getProductUseCase.getById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(id)))
                .flatMap(product -> deleteProductUseCase.delete(id));
    }
}
