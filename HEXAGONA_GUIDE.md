# Arquitectura Hexagonal con Spring Boot - Guía Paso a Paso

## Introducción
Esta guía documenta la construcción de una arquitectura hexagonal (puertos y adaptadores) en Spring Boot 3.5.7 con PostgreSQL, Google OAuth2 y Hexagonal Architecture.

---

## 1. Capa de Dominio

### 1.1 Modelo de Dominio
Crear la clase que representa tu objeto de negocio.

**Annotations:**
- `@Getter` (Lombok)
- `@NoArgsConstructor` (Lombok)
- `@AllArgsConstructor` (Lombok)
- `@Builder(toBuilder = true)` (Lombok) - El `toBuilder = true` es crucial para actualizaciones

**Características:**
- Contiene lógica de negocio pura
- Métodos de validación (`validate()`)
- Métodos de fábrica (`create()`)
- Enums para valores permitidos
- Sin dependencias de Spring o JPA

### 1.2 Excepciones de Dominio
Crear excepciones personalizadas para casos de error del negocio.

**Estructura:**
```
domain/
├── model/
│   └── MiObjeto.java
└── exception/
    ├── MiObjetoNotFoundException.java
    ├── MiObjetoValidationException.java
    └── InvalidMiObjetoTypeException.java
```

---

## 2. Capa de Puertos (Application)

### 2.1 Puertos de Entrada (Commands & Queries)
Define contratos para casos de uso que modifican (Commands) y consultan (Queries) datos.

**Annotations:**
- Interfaces sin anotaciones de Spring (puertos puros)

**Ejemplo de estructura:**
```
application/port/in/
├── CreateMiObjetoCommand.java       // Crear
├── UpdateMiObjetoCommand.java       // Actualizar
├── DeleteMiObjetoCommand.java       // Eliminar
├── GetMiObjetoByIdQuery.java        // Obtener por ID
├── GetAllMiObjetosQuery.java        // Listar con paginación
└── GetMiObjetoStatsQuery.java       // Estadísticas
```

**DTOs para solicitudes:**
- `CreateMiObjetoRequest` - Todos los campos requeridos
- `UpdateMiObjetoRequest` - Todos los campos opcionales

### 2.2 Puerto de Salida (Repository)
Define el contrato para persistencia de datos.

**Annotations:**
- Interfaz sin anotaciones de Spring

**Métodos típicos:**
- `save(MiObjeto)` - Guardar nuevo
- `update(MiObjeto)` - Actualizar existente
- `findById(id, userId)` - Obtener por ID con isolamiento de datos
- `findAll(userId, filters)` - Listar con filtros
- `delete(id, userId)` - Eliminar
- Métodos de validación (`existsByIdAndUserId()`, `hasAssociatedResources()`)

**DTOs para repository:**
- `PaginationParams` - Parámetros de paginación seguros
- `MiObjetoFilter` - Filtros opcionales

---

## 3. Capa de Aplicación (Services)

### 3.1 Servicios de Caso de Uso
Implementan los puertos de entrada (Commands y Queries).

**Annotations:**
- `@Service` (Spring)
- `@RequiredArgsConstructor` (Lombok) - Inyección de dependencias
- `@Transactional` (Spring) - Para modificaciones
- `@Transactional(readOnly = true)` - Para consultas
- `@Slf4j` (Lombok) - Logging

**Responsabilidades:**
- Orquestar la lógica de casos de uso
- Validar datos usando el repositorio
- Llamar al dominio para operaciones
- Persistir cambios

**Estructura:**
```
application/service/
├── CreateMiObjetoService.java
├── UpdateMiObjetoService.java
├── DeleteMiObjetoService.java
├── GetMiObjetoByIdService.java
├── GetAllMiObjetosService.java
└── GetMiObjetoStatsService.java
```

---

## 4. Capa de Infraestructura - Persistencia

### 4.1 Entidad JPA
Mapeo de la tabla de base de datos.

**Annotations:**
- `@Entity` (JPA)
- `@Table(name = "...", schema = "...")` (JPA)
- `@Id` (JPA)
- `@Column` (JPA)
- `@CreationTimestamp` (Hibernate)
- `@UpdateTimestamp` (Hibernate)
- `@Getter`, `@Setter` (Lombok)
- `@NoArgsConstructor`, `@AllArgsConstructor` (Lombok)
- `@Builder` (Lombok)

**Características:**
- Espejo exacto del modelo de dominio
- Nombres en snake_case
- Índices para consultas frecuentes
- Restricciones de base de datos

### 4.2 Repositorio JPA Spring Data
Interfaz de queries a base de datos.

**Annotations:**
- `@Repository` (Spring) - Opcional, Spring la detecta automáticamente

**Métodos:**
- Métodos simples por nombre (`findByIdAndStudentId()`)
- Métodos complejos con `@Query` (JPQL/SQL)
- Retornan `Optional<>` para opcionalidad segura
- Soportan `Page<>` para paginación

### 4.3 Mapper Entidad-Dominio
Convierte entre JPA Entity y Dominio.

**Annotations:**
- `@Component` (Spring)

**Métodos:**
- `toEntity(domainObject)` - Dominio a JPA
- `toDomain(jpaEntity)` - JPA a Dominio

### 4.4 Adaptador de Persistencia (Repository Adapter)
Implementa el puerto de salida usando JPA.

**Annotations:**
- `@Component` (Spring) o `@Repository`
- `@RequiredArgsConstructor` (Lombok)
- `@Slf4j` (Lombok)

**Responsabilidades:**
- Implementar todas las operaciones del puerto
- Usar el mapper para conversiones
- Manejar la lógica de paginación
- Convertir excepciones JPA a excepciones de dominio

---

## 5. Capa de Infraestructura - Web

### 5.1 DTOs HTTP
Modelos para solicitudes y respuestas REST.

**Annotations para Request DTOs:**
- `@NotBlank`, `@NotNull` (Jakarta Validation)
- `@Size`, `@Min`, `@Max`, `@Pattern` (Jakarta Validation)
- `@JsonFormat(pattern = "yyyy-MM-dd")` (Jackson)
- `@JsonProperty` (Jackson)
- `@Getter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder` (Lombok)
- `@Schema` (SpringDoc OpenAPI)

**Annotations para Response DTOs:**
- `@JsonProperty` (Jackson)
- `@Schema` (SpringDoc OpenAPI)
- `@Getter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder` (Lombok)
- **Nota:** NO usar `@JsonFormat` en responses, confunde a Jackson

### 5.2 Mapper HTTP
Convierte entre DTOs HTTP y modelos de aplicación.

**Annotations:**
- `@Component` (Spring)

**Métodos:**
- `toCreateCommandRequest(httpRequest)`
- `toUpdateCommandRequest(httpRequest)`
- `toHttpResponse(domainObject)`
- `toPaginationHttpResponse(appResponse)`

### 5.3 Controlador REST
Expone endpoints HTTP.

**Annotations:**
- `@RestController` (Spring)
- `@RequestMapping("/path")` (Spring)
- `@RequiredArgsConstructor` (Lombok)
- `@Slf4j` (Lombok)
- `@Tag` (SpringDoc OpenAPI)
- `@SecurityRequirement(name = "bearer-jwt")` (SpringDoc OpenAPI)

**Métodos de endpoint:**
- `@PostMapping`, `@GetMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping` (Spring)
- `@PathVariable`, `@RequestParam`, `@RequestBody` (Spring)
- `@Valid` (Jakarta Validation)
- `@Parameter`, `@Operation`, `@ApiResponse`, `@ApiResponses` (SpringDoc OpenAPI)

**Responsabilidades:**
- Recibir y validar solicitudes HTTP
- Extraer contexto de autenticación
- Mapear a comandos/queries
- Mapear respuestas a DTOs HTTP
- Retornar códigos HTTP apropiados

### 5.4 Manejador Global de Excepciones
Convierte excepciones de dominio a respuestas HTTP.

**Annotations:**
- `@RestControllerAdvice` (Spring)
- `@ExceptionHandler` (Spring)
- `@Slf4j` (Lombok)

**Handlers:**
- Por cada excepción de dominio
- Por errores de validación (`MethodArgumentNotValidException`)
- Por errores HTTP (`HttpMessageNotReadableException`)
- Fallback general para excepciones no manejadas

---

## 6. Capa de Infraestructura - Seguridad

### 6.1 Autenticación con Google OAuth2
Validar tokens de Google.

**Annotations:**
- `@Component` (Spring)
- `@RequiredArgsConstructor` (Lombok)
- `@Slf4j` (Lombok)

**Componentes:**
- `GoogleTokenValidator` - Valida tokens con Google
- `GoogleTokenAuthenticationFilter` - Extrae y valida tokens en cada request
- `GoogleAuthenticationUtil` - Helper para acceder a datos del usuario

### 6.2 Configuración de Spring Security
Define reglas de autorización y CORS.

**Annotations:**
- `@Configuration` (Spring)
- `@EnableWebSecurity` (Spring Security)
- `@Bean` (Spring)

**Métodos:**
- `filterChain()` - Configurar filtros de seguridad
- `corsConfigurationSource()` - Configurar CORS

---

## 7. Configuración General

### 7.1 Application.yml
Centralizar todas las configuraciones.

**Secciones principales:**
```yaml
spring:
  datasource:          # Conexión a DB
  jpa:                 # Configuración JPA/Hibernate
  flyway:              # Migraciones de base de datos
  jackson:             # Serialización JSON
  security.oauth2:     # OAuth2 de Google
  web.cors:            # CORS

server:               # Puerto y contexto
logging:              # Niveles de log
```

### 7.2 Migraciones Flyway
SQL para crear tablas y esquemas.

**Estructura:**
```
src/main/resources/db/migration/
├── V1__create_schema.sql
├── V2__create_miobjeto_table.sql
└── V3__add_indexes.sql
```

**Naming:** `V{version}__{description}.sql`

---

## 8. Flujo Completo de una Solicitud

```
1. HTTP Request → PeriodStatsFilter (OAuth2)
   ↓
2. GoogleTokenAuthenticationFilter valida token
   ↓
3. PeriodController recibe request
   ↓
4. Mapper HTTP convierte a Command/Query
   ↓
5. Service ejecuta lógica de caso de uso
   ↓
6. Repository persiste/recupera datos
   ↓
7. Mapper Entity ↔ Domain
   ↓
8. Service retorna resultado de dominio
   ↓
9. Mapper Response convierte a DTO HTTP
   ↓
10. Controller retorna ResponseEntity
   ↓
11. PeriodExceptionHandler maneja errores
   ↓
12. HTTP Response al cliente
```