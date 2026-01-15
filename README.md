
# Punto de Venta – API Controllers

Este documento describe los endpoints expuestos por los controladores del módulo **Punto de Venta**, junto con los DTOs involucrados y ejemplos de requests/responses.

> Framework: Spring Boot 3 / Spring 6  
> Idioma: Español  
> Base URL por entorno (ejemplo): `http://localhost:8080`

---

## Índice

- [Arquitectura (alto nivel)](#arquitectura-alto-nivel)
- [POSController](#poscontroller)
    - [GET /v1/point-of-sale/findAll](#get-v1point-of-salefindall)
    - [POST /v1/point-of-sale/create](#post-v1point-of-salecreate)
    - [PUT /v1/point-of-sale/{id}](#put-v1point-of-saleid)
    - [DELETE /v1/point-of-sale/{id}](#delete-v1point-of-saleid)
- [PointOfSaleRouteCostController](#pointofsaleRouteCostController)
    - [POST /v1/point-of-sale-cost/create](#post-v1point-of-sale-costcreate)
    - [DELETE /v1/point-of-sale-cost/remove](#delete-v1point-of-sale-costremove)
    - [GET /v1/point-of-sale-cost/findAll](#get-v1point-of-sale-costfindall)
    - [GET /v1/point-of-sale-cost/showMinimumRoutes](#get-v1point-of-sale-costshowminimumroutes)
- [DTOs](#dtos)
- [Códigos de estado](#códigos-de-estado)
- [Ejecutar localmente](#ejecutar-localmente)
- [Testing](#testing)
- [Mejoras sugeridas](#mejoras-sugeridas)

---

## Arquitectura (alto nivel)

- **Controllers**: exponen endpoints REST.
- **Services**: encapsulan la lógica de negocio (`POSService`, `GraphPOSService`).
- **DTOs**: objetos de transferencia para requests/responses.
- **Mapper**: `ResponseGraphPOSMapper` para mapear requests del grafo a entidad.

---

## POSController

**Paquete**: `com.challenge_2026.punto_de_venta_acc.controller`  
**Base path**: `/v1/point-of-sale`

Controlador para operaciones sobre **Puntos de Venta** (`PointOfSale`).

### GET /v1/point-of-sale/findAll

- **Descripción**: Obtiene todos los puntos de venta.
- **Respuesta**: `200 OK` con `List<POSDto>`.
- **Ejemplo (curl)**:
  ```bash
  curl -X GET "http://localhost:8080/v1/point-of-sale/findAll"
  ```
- **Ejemplo (JSON)**:
  ```json
  [
    { "id": "POS-001", "name": "Sucursal Centro" },
    { "id": "POS-002", "name": "Sucursal Norte" }
  ]
  ```

### POST /v1/point-of-sale/create

- **Descripción**: Crea un punto de venta.
- **Body**: `CreatePOSRequest`
- **Respuesta**: `201 Created`
    - **Location**: `/v1/point-of-sale/{id}`
    - **Body**: `null` (según implementación actual del controlador)
- **Ejemplo (curl)**:
  ```bash
  curl -X POST "http://localhost:8080/v1/point-of-sale/create" \
       -H "Content-Type: application/json" \
       -d '{
             "id": "POS-003",
             "name": "Sucursal Oeste"
           }' -i
  # Revisar encabezado Location en la respuesta
  ```
- **Notas**:
    - El servicio crea la entidad a partir de `PointOfSale.from(request)`.

### PUT /v1/point-of-sale/{id}

- **Descripción**: Actualiza el **nombre** del POS por `id`.
- **Path**: `id` (Long)
- **Body**: `UpdatePOSRequest`
- **Respuesta**:
    - `200 OK` con el mismo `UpdatePOSRequest` en el body.
    - `404 Not Found` si el POS no existe (lanzado por `POSService.updateNamePointOfSale`).
- **Ejemplo (curl)**:
  ```bash
  curl -X PUT "http://localhost:8080/v1/point-of-sale/123" \
       -H "Content-Type: application/json" \
       -d '{ "name": "Sucursal Actualizada" }'
  ```

### DELETE /v1/point-of-sale/{id}

- **Descripción**: Elimina el POS por `id`.
- **Respuesta**: `200 OK` con body `"POS Deleted"`.
- **Ejemplo (curl)**:
  ```bash
  curl -X DELETE "http://localhost:8080/v1/point-of-sale/123"
  ```

---

## PointOfSaleRouteCostController

**Paquete**: `com.challenge_2026.punto_de_venta_acc.controller`  
**Base path**: `/v1/point-of-sale-cost`

Gestiona **rutas** y **costos** entre puntos de venta y el cálculo de **rutas mínimas**.

### POST /v1/point-of-sale-cost/create

- **Descripción**: Crea una ruta entre `pointA` y `pointB` con un `cost`.
- **Body**: `CreateGraphPOSRequest` (validado con `@Valid`)
- **Respuesta**: `201 Created`
    - **Location**: `/v1/point-of-sale-cost`
    - **Body**: `GraphPOSResponse` con mensaje generado en el controlador:
      ```
      "Se creo el camino entre" + pointA + " y " + pointB + "con un costo de: " + cost
      ```
      > Nota: en la implementación actual **no** hay espacio entre "entre" y `pointA`, ni antes de "con un costo de".
- **Ejemplo (curl)**:
  ```bash
  curl -X POST "http://localhost:8080/v1/point-of-sale-cost/create" \
       -H "Content-Type: application/json" \
       -d '{ "pointA": "POS-001", "pointB": "POS-002", "cost": 10 }'
  ```

### DELETE /v1/point-of-sale-cost/remove

- **Descripción**: Elimina la ruta entre `pointA` y `pointB`.
- **Body**: `DeleteGraphPOSRequest` (validado con `@Valid`)
- **Respuesta**: `201 Created`
    - **Location**: `/v1/point-of-sale-cost/remove`
    - **Body**: `GraphPOSResponse` con mensaje:
      ```
      "Se removio con exito el camino entre " + pointA + " y " + pointB
      ```
- **Ejemplo (curl)**:
  ```bash
  curl -X DELETE "http://localhost:8080/v1/point-of-sale-cost/remove" \
       -H "Content-Type: application/json" \
       -d '{ "pointA": "POS-001", "pointB": "POS-002" }'
  ```

### GET /v1/point-of-sale-cost/findAll

- **Descripción**: Lista todas las rutas/costos registradas.
- **Respuesta**: `200 OK` con `List<GraphPOSDto>`.
- **Ejemplo (curl)**:
  ```bash
  curl -X GET "http://localhost:8080/v1/point-of-sale-cost/findAll"
  ```

### GET /v1/point-of-sale-cost/showMinimumRoutes

- **Descripción**: Devuelve las rutas mínimas calculadas por el servicio.
- **Respuesta**: `200 OK` con `List<MinimumGraphPOSDto>`.
- **Ejemplo (curl)**:
  ```bash
  curl -X GET "http://localhost:8080/v1/point-of-sale-cost/showMinimumRoutes"
  ```

---

## DTOs

A continuación, se documentan los DTOs expuestos en el paquete `com.challenge_2026.punto_de_venta_acc.dto`.

### CreatePOSRequest
```java
public record CreatePOSRequest(String id, String name) {}
```
**JSON de ejemplo**:
```json
{ "id": "POS-003", "name": "Sucursal Oeste" }
```

### UpdatePOSRequest
```java
public record UpdatePOSRequest(String name) {}
```
**JSON de ejemplo**:
```json
{ "name": "Sucursal Actualizada" }
```

### POSDto
```java
public class POSDto {
    private String id;
    private String name;
    // getters / setters / ctor
}
```
**JSON de ejemplo**:
```json
{ "id": "POS-001", "name": "Sucursal Centro" }
```

### POSResponse
```java
public record POSResponse(String id, String name) {
    public static POSResponse created(String id, String name, String location) {
        return new POSResponse(id, name);
    }
}
```
**JSON de ejemplo**:
```json
{ "id": "POS-003", "name": "Sucursal Oeste" }
```

### CreateGraphPOSRequest
```java
public record CreateGraphPOSRequest(
    @NotBlank String pointA,
    @NotBlank String pointB,
    @NotNull @Positive Integer cost
) {}
```
**JSON de ejemplo**:
```json
{ "pointA": "POS-001", "pointB": "POS-002", "cost": 10 }
```

### DeleteGraphPOSRequest
```java
public record DeleteGraphPOSRequest(
    @NotBlank String pointA,
    @NotBlank String pointB
) {}
```
**JSON de ejemplo**:
```json
{ "pointA": "POS-001", "pointB": "POS-002" }
```

### GraphPOSDto
```java
public class GraphPOSDto {
    private String originPOS;
    private String destionationPOS; // (sic) nombre de campo actual en el código
    private Integer cost;
    // getters / setters / ctor
}
```
**JSON de ejemplo**:
```json
{ "originPOS": "POS-001", "destionationPOS": "POS-002", "cost": 10 }
```
> Nota: el campo se llama `destionationPOS` en el código actual. Si se desea, puede normalizarse a `destinationPOS` en una refactorización posterior (breaking change en el contrato JSON).

### MinimumGraphPOSDto
```java
public class MinimumGraphPOSDto {
    private String originPOS;
    private String destionationPOS; // (sic)
    private Integer minimumCost;
    // getters / setters / ctor
}
```
**JSON de ejemplo**:
```json
{ "originPOS": "POS-001", "destionationPOS": "POS-003", "minimumCost": 15 }
```

### GraphPOSResponse
```java
public record GraphPOSResponse(String message) {}
```
**JSON de ejemplo**:
```json
{ "message": "Se creo el camino entrePOS-001 y POS-002con un costo de: 10" }
```

---

## Códigos de estado

- `200 OK`: operaciones `GET` exitosas y `PUT` exitoso de `POSController`.
- `201 Created`: creación de recursos y la operación `DELETE /point-of-sale-cost/remove` (según implementación actual del controlador).
- `404 Not Found`: cuando se intenta actualizar un POS inexistente en `PUT /point-of-sale/{id}`.

---

## Ejecutar localmente

1. **Requisitos**: JDK 17+, Maven/Gradle, puerto libre `8080` (o configurar `server.port`).
2. **Build & Run (Maven)**:
   ```bash
   mvn clean spring-boot:run
   ```
3. **Health check**: probar `GET /v1/point-of-sale/findAll`.

---

## Testing

- **Unit tests**: JUnit 5 + Mockito, invocando directamente los métodos del controlador.
- **Tips**:
    - En Spring 6, `ResponseEntity#getStatusCode()` devuelve `HttpStatusCode`; comparar con `HttpStatus` o usar `getStatusCodeValue()` si se compara con `int`.
    - Verificar encabezados `Location` en respuestas `201`.

---

## Mejoras sugeridas

- Homogeneizar los mensajes de `GraphPOSResponse` (agregar espacios faltantes en `create`) para legibilidad.
- Evaluar responder `200 OK` en `DELETE /point-of-sale-cost/remove` si se busca una semántica REST más conservadora.
- Normalizar `destionationPOS` → `destinationPOS` (quebraría el contrato JSON actual; considerar versión de API o mapeo de compatibilidad).

---

© 2026 — Punto de Venta API
