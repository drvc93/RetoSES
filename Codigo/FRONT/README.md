# Reto técnico Front-End

SPA en React + TypeScript que carga productos desde `products.json`, permite buscar, filtrar por categoría y gestionar un carrito con persistencia en `localStorage`.

## Cómo ejecutar

```bash
npm install
npm run dev
```

Para pruebas:

```bash
npm run test
```

## Decisiones técnicas

- Se implementó con React 19 + Vite + TypeScript por simplicidad y rapidez de arranque.
- Se copia el archivo provisto a `src/data/products.json` para versionar una fuente local estable.
- Se simula latencia inicial con `setTimeout` para mostrar el estado "Cargando...".
- Se usó `useMemo` para categorías, lista filtrada y totales del carrito.
- El carrito persiste en `localStorage` con la clave `front-tech-cart`.
- Se añadieron etiquetas y atributos de accesibilidad (`aria-label`, `aria-live`, `role`).
- Se incluye un test funcional con React Testing Library.

## Mejoras pendientes

- Separar `App.tsx` en componentes pequeños (`ProductList`, `Cart`, `Filters`) para escalar.
- Agregar más tests (filtro por categoría, eliminación de ítems, persistencia).
- Integrar un sistema de diseño y manejo más robusto de estados de error.
