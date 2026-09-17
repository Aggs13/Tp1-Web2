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
