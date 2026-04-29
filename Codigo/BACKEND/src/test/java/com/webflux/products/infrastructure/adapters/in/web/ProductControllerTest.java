package com.webflux.products.infrastructure.adapters.in.web;

import com.webflux.products.domain.model.Product;
import com.webflux.products.domain.port.in.CreateProductUseCase;
import com.webflux.products.domain.port.in.DeleteProductUseCase;
import com.webflux.products.domain.port.in.GetProductUseCase;
import com.webflux.products.domain.port.in.ListProductsUseCase;
import com.webflux.products.domain.port.in.UpdateProductUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebFluxTest(controllers = ProductController.class)
@Import({ApiExceptionHandler.class, ProductControllerTest.MockConfig.class})
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CreateProductUseCase createProductUseCase;

    @Test
    void shouldCreateProduct() {
        Product created = new Product(
                1L,
                "P900",
                "Fondo WebFlux",
                "Demo",
                BigDecimal.valueOf(999.99),
                "Fondos",
                LocalDateTime.now(),
                null,
                true
        );

        Mockito.when(createProductUseCase.create(Mockito.any())).thenReturn(Mono.just(created));

        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "code": "P900",
                          "name": "Fondo WebFlux",
                          "description": "Demo",
                          "price": 999.99,
                          "category": "Fondos",
                          "state": true
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.code").isEqualTo("P900");
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        CreateProductUseCase createProductUseCase() {
            return Mockito.mock(CreateProductUseCase.class);
        }

        @Bean
        GetProductUseCase getProductUseCase() {
            return Mockito.mock(GetProductUseCase.class);
        }

        @Bean
        ListProductsUseCase listProductsUseCase() {
            return Mockito.mock(ListProductsUseCase.class);
        }

        @Bean
        UpdateProductUseCase updateProductUseCase() {
            return Mockito.mock(UpdateProductUseCase.class);
        }

        @Bean
        DeleteProductUseCase deleteProductUseCase() {
            return Mockito.mock(DeleteProductUseCase.class);
        }
    }
}
