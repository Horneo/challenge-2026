Correr con este comando en podman: podman-compose up -d --build --force-recreate
Luego probarlo en el postman con los endpoint de punto de venta y acreditaciones.

---
Pasos a ejecutar para correr el caso de uso de todos los enunciados de punto de venta(enunciado 1, 2 y 3)
1) Levantar el docker composer con podman-compose - esto te crea un pod_punto-de-venta-acc
  podman-compose up mongo redis pos-service acreditaciones-service
2) Una vez levantado el pod, ir cargar la coleccion postman y ejecutar enunciado 1, 2 y 3 que corresponden a este ms.
---
# Challenge 2026 - Punto de Venta API y Acreditaciones API
# Enunciado 1, 2 y 3 - API Controllers
Enunciado
Se requiere implementar una aplicación java que exponga un api por http para cumplir los siguientes casos de uso:

1 Caché de puntos de venta
La aplicación deberá contener un cache en memoria de "puntos de venta", de los que se conoce su identificador numérico y el nombre de este.
Información inicial de puntos de venta:
Id, Nombre
1, CABA
2, GBA_1
3, GBA_2
4, Santa Fe
5, Córdoba
6, Misiones
7, Salta
8, Chubut
9, Santa Cruz
10, Catamarca

Para este cache de puntos de venta se requerirán endpoints HTTP para
(1) Recuperar todos los puntos de venta presentes en el cache
(2) Ingresar un nuevo punto de venta
(3) Actualizar un punto de venta
(4) Borrar un punto de venta

2 Caché de costos entre los puntos de venta
También se deberá poseer un caché en memoria que guarde el costo (numérico) de ir de un punto de venta a otro. Se comprende que
- el costo nunca podría ser menor a cero
- el costo de ir a un punto de venta a si mismo es irrisorio, 0
- el costo de ir del punto de venta A al punto de venta B siempre será igual a hacer el camino inverso.
- no todos los puntos de venta están directamente conectados, y puede que un punto de venta B sea inalcanzable desde un punto A
- si un punto de venta A está comunicado directamente con un punto de venta B ese camino es único y no se posee un camino directo paralelo
- el camino más directo entre dos puntos puede no ser el menos costoso

Información inicial de costos entre puntos de venta:
IdA, IdB, Costo
1,2,2
1,3,3
2,3,5
2,4,10
1,4,11
4,5,5
2,5,14
6,7,32
8,9,11
10,7,5
3,8,10
5,8,30
10,5,5
4,6,6

Para este cache de costos entre puntos de venta se requerirán endpoints HTTP para
(1) Cargar un nuevo costo entre un punto de venta A y un punto de venta B (crearía un camino directo entre A y B con el costo indicado)
(2) Remover un costo entre un punto de venta A y un punto de venta B (removería en caso de existir un camino directo entre A y B)
(3) Consultar los puntos de venta directamente a un punto de venta A, y los costos que implica llegar a ellos
(4) Consultar el camino con costo mínimo entre dos puntos de venta A y B. (indicar el costo mínimo, y el camino realizado, aprovechando los nombres de los puntos de venta del caché del punto anterior)

3 Acreditaciones
Además del control de cache de los puntos de venta se requiere otro end point http que reciba "acreditaciones".
La información relevante de la acreditación que se recibirá de forma externa es un importe y un identificador de punto de venta.
Con esa información provista debemos enriquecer esta acreditación con
(1) la fecha en la que se recibió el pedido
(2) el nombre del punto de venta que le corresponde consultando el cache en memoria, o fallando si el punto de venta no existe

Esta información enriquecida deberá ser persistida en una BBDD (preferentemente no una en memoria, y que sea externa a la aplicación) los atributos que deben persistirse son
- importe
- identificador de punto de venta
- fecha de recepción
- nombre del punto de venta

Esta misma información deberá poder ser consultable a través de otro end point http.

Como parte de la resolución
(1) indique como debe compilarse el código fuente, incluya comandos o scripts que deban tenerse en cuenta, es deseable la posibilidad de correr la aplicación en un contenedor Docker, pero no es imperativo.
(2) indique cómo debe ejecutarse este compilado / imagen creada en el punto anterior y que requisitos requiere en el host para poder realizarlo.
(3) indique como puede probarse la aplicación en los casos de uso del enunciado
(4) En caso de asumir supuestos por favor dejen los indicados.


Tenga en cuenta
(1) La aplicación entregada deberá poder soportar un alto grado de concurrencia sin que se presenten errores causados por la implementación realizada.
(2) Serán apreciadas inclusiones de nuevas utilidades disponibles en las últimas versiones de Java.
Indicar cuales fueron en caso de incluirse, cómo funcionan, en qué versión fueron incluidas.
(3) Será apreciado el uso de patrones de diseño.
Indicar cuales fueron en caso de incluirse.
(4) Será apreciado diagramas de clases / tablas / secuencias (que considere prácticos)
No adjuntar archivos que requieran de una herramienta específica para poder ser abiertos, salvo que la misma pueda ser fácilmente utilizable de forma web.
Lo ideal sería que los diagramas puedan renderizarse de forma natural y nativa en el repositorio entregado.
(4) El código fuente debe contar con test unitarios. Es deseable que se alcance un % de cobertura mayor al 70%, sin incluir en la solución 'smells', 'bugs', 'issues' o dependencias que posean vulnerabilidades críticas (esto último en la medida de que existan nuevas versiones que posean dichas vulnerabilidades corregidas). En lo posible adjunte un reporte que indique de estos resultados.

La resolución deberá ser presentada en un repositorio git con visibilidad pública para que pueda ser evaluado.
Es posible que distintos aspectos de la resolución deberán ser defendidos durante una próxima entrevista técnica.


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

Mejoras:
1)Al actualizar un punto de venta verificar que no sea el mismo que ya esta ingresado
2)Usar el algoritmo de Dijkstra para calcular la ruta minima entre dos puntos.
3)Agregar validacion en la acreditaciones para arrojar exception cuando el punto de venta no exista.

© 2026 — Punto de Venta API
