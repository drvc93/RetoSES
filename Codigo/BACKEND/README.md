# Backend - Prueba Técnica WebFlux

Microservicio reactivo construido con Java 17, Spring Boot WebFlux, arquitectura hexagonal, H2 en memoria (persistente durante la ejecución) y Gradle.

## Tecnologías

- Java 17
- Spring Boot 3 (WebFlux + R2DBC)
- H2
- Gradle
- JUnit 5 + Mockito

## Arquitectura

Se implementó una arquitectura hexagonal con las capas:

- `domain`: modelo y puertos (in/out).
- `application`: casos de uso (servicio de negocio).
- `infrastructure`: adaptadores REST (entrada), persistencia R2DBC (salida) y configuración.

## Entidad Products

- `id` (Long)
- `code` (varchar 10)
- `name` (varchar 100)
- `description` (varchar 200)
- `price` (decimal 10,2)
- `category` (varchar 100)
- `reg_date` (timestamp)
- `mod_date` (timestamp)
- `state` (boolean)

## Endpoints

- `POST /api/products` crear producto
- `GET /api/products/{id}` obtener por id
- `GET /api/products` listar productos
- `PUT /api/products/{id}` actualizar producto
- `DELETE /api/products/{id}` eliminar producto

## Ejecución

```bash
./gradlew bootRun
```

En Windows:

```bash
gradlew.bat bootRun
```

## Testing

```bash
./gradlew test
```

Se incluyen:

- prueba de servicio (casos de uso)
- prueba de endpoint reactivo con WebTestClient
