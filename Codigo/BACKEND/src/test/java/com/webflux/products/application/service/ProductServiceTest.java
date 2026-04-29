package com.webflux.products.application.service;

import com.webflux.products.domain.model.Product;
import com.webflux.products.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort repositoryPort;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(repositoryPort);
    }

    @Test
    void shouldCreateProductWithRegDate() {
        Product input = new Product(
                null,
                "P100",
                "Fondo Prueba",
                "Desc",
                BigDecimal.valueOf(200),
                "Fondos",
                null,
                null,
                true
        );

        when(repositoryPort.save(any(Product.class))).thenAnswer(invocation -> {
            Product sent = invocation.getArgument(0);
            return Mono.just(new Product(
                    1L,
                    sent.code(),
                    sent.name(),
                    sent.description(),
                    sent.price(),
                    sent.category(),
                    sent.regDate(),
                    sent.modDate(),
                    sent.state()
            ));
        });

        StepVerifier.create(productService.create(input))
                .assertNext(created -> {
                    assertEquals(1L, created.id());
                    assertNotNull(created.regDate());
                    assertNull(created.modDate());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateProductAndSetModDate() {
        Product existing = new Product(
                1L,
                "P001",
                "Fondo Conservador",
                "Desc",
                BigDecimal.valueOf(100),
                "Fondos",
                LocalDateTime.now().minusDays(1),
                null,
                true
        );
        Product update = new Product(
                null,
                "P001",
                "Fondo Conservador Plus",
                "Desc nuevo",
                BigDecimal.valueOf(150),
                "Fondos",
                null,
                null,
                true
        );

        when(repositoryPort.findById(1L)).thenReturn(Mono.just(existing));
        when(repositoryPort.save(any(Product.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productService.update(1L, update))
                .assertNext(updated -> {
                    assertEquals(1L, updated.id());
                    assertNotNull(updated.modDate());
                    assertEquals("Fondo Conservador Plus", updated.name());
                })
                .verifyComplete();
    }
}
