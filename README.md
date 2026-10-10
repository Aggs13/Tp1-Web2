# TP1 — Cómo levantar y probar (VS Code)

> Puerto usado: **8081** (el 8080 estaba ocupado, ver `application.properties` si es necesario cambiarlo).

## 1. Levantar
1. Abrí la carpeta `Tp1` en VS Code.
2. Abrí `src/main/java/Web2/Tp1/Tp1Application.java`.
3. Apretá **Run** arriba del `main`.
4. Esperá `Started Tp1Application`.

## 2. Probar en Swagger
Abrí: http://localhost:8081/swagger-ui.html

Orden:
1. `GET /api/productos/{id}` → Busca un producto.
2. `GET /api/favoritos` → 3 objetos favoritos precargados en `FavoritosRepositoryImplement.java` (ids 1,2,3).
3. `POST /api/favoritos` → Agrega un nuevo favorito a la lista

## Caso Exito
### Request body
```json
{
  "idProducto": 10,
  "notaPersonal": "Comprar mas de esto"
}
```
### Response body

```json
{
  "mensaje": "Se agrego el producto correctamente",
  "datos": {
    "id": 4,
    "notaPersonal": "Comprar mas de esto",
    "fechaAgregado": "2026-09-17",
    "productoFavorito": {
      "id": 10,
      "title": "Gucci Bloom Eau de",
      "price": 79.99
    }
  },
  "estado": 201
}
```

## Caso Error
```json
{
  "detail": "Ocurrió un error inesperado",
  "instance": "/api/favoritos",
  "status": 500,
  "title": "Error interno"
}
```

Cada endpoint tiene `Try it out → Execute`.

---

# TP2 — Persistencia, migraciones y arquitectura hexagonal

## 4. Puertos y adaptadores: qué cambió y qué no

**Tuve que tocar (infraestructura):**
- Eliminado el repository en memoria; nuevo `FavoritoRepositoryAdapter` + `FavoritoEntity` + `FavoritoJpaRepository` (y lo mismo para `Lista`).
- Nuevas migraciones Flyway `V1–V5` y `ddl-auto=validate` (el esquema lo maneja Flyway, no Hibernate).

**Quedaron iguales (dominio y aplicación):**
- `model` (`Favorito`, `Lista`), services (`FavoritosService`, `ListaService`), controllers y DTOs (solo sumaron `listaId` por el punto 5 y se ordenaron en carpetas).
- `GlobalExceptionHandler` solo sumó casos (`400` por id inválido, `409` por conflicto), no cambió su forma.

**Por qué fue posible:** los services dependen del puerto (`FavoritosRepository` / `ListaRepository`, una interfaz) y no de quién lo implementa. Migrar de memoria a JPA fue cambiar el bean detrás de esa misma interfaz (el adapter), sin tocar el contrato; por eso el service ni se enteró.

## 6. Evolución del esquema

Esto se resuelve con una migración nueva (`V5__lista_id_obligatorio.sql`) y nunca editando una ya aplicada, porque Flyway guarda el checksum de cada migración corrida: si modificás `V1`–`V3` después de aplicadas, la app no arranca. `V5` crea "Sin clasificar", reasigna los `lista_id NULL` y recién ahí declara el `NOT NULL`.

## 7. Transacciones

`POST /api/listas/{origenId}/mover-favoritos` hace N `UPDATE`s (reasignar cada favorito) + 1 `DELETE` (la lista origen) bajo un `@Transactional` en `ListaService.moverFavoritos`. Sin él, si el `DELETE` fallara después de los `UPDATE`s ya confirmados, quedaría una mudanza a medias (favoritos en destino con la origen todavía viva), violando la atomicidad de ACID. Con `@Transactional` es todo o nada: ante un fallo hace rollback y la base queda como antes.
