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

## 8. Documentación

Swagger ya muestra `productos`, `favoritos` y `listas` (`@Tag` + `@Operation` con descripción en cada endpoint). Abrir: http://localhost:8081/swagger-ui.html

### Levantar PostgreSQL

1. Copiar `.env.example` como `.env` y completar la contraseña.
2. `docker compose up -d` (imagen `postgres:16`, contenedor `tp2-postgres`, puerto `5433`, datos en volumen `pgdata`).
3. Correr la app (`Tp1Application`). Sin Docker: instalación local de PostgreSQL con los mismos datos de conexión del `.env`.
4. El esquema lo aplica Flyway (`ddl-auto=validate`, Hibernate no toca el DDL).

### Confirmar migraciones

- En el log al arrancar: `Flyway ... successfully applied N migrations`.
- En la DB: `SELECT version, description, success FROM flyway_schema_history ORDER BY installed_rank;` (deben estar `V1`–`V5` en `t`).

---

# TP3 — Seguridad (JWT) y Autorización

## 4. Autorización: dónde vive la regla y por qué

La regla de autorización sobre la propiedad de las listas (`validarPropiedadLista`, que verifica que `lista.usuarioId` coincida con el usuario autenticado en el `SecurityContext` o que tenga el rol `ROLE_ADMIN`) vive en la **capa de Servicio (`ListaServiceImplement`)** y no en el `Controller` ni en un filtro HTTP:

- **¿Por qué en el Service y no en el Controller?**
  1. **Es una regla de negocio / dominio:** Decidir primero si la lista existe (`404 Not Found`) y luego si el usuario actual es dueño o `ADMIN` (`403 Forbidden` mediante `AccesoDenegadoException`) requiere consultar el repositorio (`BuscarListaPorId`). El Controller solo debe ocuparse de recibir el request HTTP, validar el formato de entrada y devolver la respuesta, sin mezclar lógica de negocio ni acceso a datos.
  2. **Reutilización y consistencia:** Si el día de mañana otro caso de uso o servicio interno invoca los métodos de `ListaService` (por ejemplo, al mover favoritos entre dos listas donde hay que validar tanto la lista origen como la destino), la protección se aplica en un único lugar sin duplicar validaciones en cada endpoint del Controller.
  3. **¿Por qué no en el filtro JWT?** El filtro (`JwtFilter`) trabaja a nivel de URL/transporte autenticando el token antes de llegar a la lógica de negocio; no conoce ni debe consultar la base de datos para saber a qué `usuario_id` pertenece el `{id}` de una lista puntual.
