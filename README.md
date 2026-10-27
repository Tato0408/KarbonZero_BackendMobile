# KarbonZero — API Central (Móvil y Gestión)

> API REST principal del ecosistema **KarbonZero**: registra la huella de carbono personal de los usuarios de la app móvil (transporte, vehículos, alimentación, servicios, vivienda y mascotas), administra los catálogos del sistema y gestiona las **empresas** afiliadas y sus **canales** de comunicación.

---

## Contenido

- [Descripción](#descripción)
- [Ecosistema KarbonZero](#ecosistema-karbonzero)
- [Autores](#autores)
- [Stack Tecnológico](#stack-tecnológico)
- [Requisitos previos](#requisitos-previos)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Librerías principales y su función](#librerías-principales-y-su-función)
- [Convenciones de nomenclatura](#convenciones-de-nomenclatura)
- [Endpoints de la API](#endpoints-de-la-api)
- [Base de datos](#base-de-datos)
- [Variables de entorno](#variables-de-entorno)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [Solución de problemas comunes](#solución-de-problemas-comunes)

---

## Descripción

KarbonZero busca que cada persona conozca, registre y reduzca su huella de carbono. Esta API es el **núcleo** del sistema y atiende a dos clientes:

- **App móvil (usuarios finales):**
  - Registro, inicio de sesión y recuperación de contraseña por código enviado al correo.
  - Registro de consumos que generan CO₂: **transporte**, **combustible de vehículos propios**, **alimentación**, **servicios** (agua, luz, gas…), **vivienda** y **mascotas**.
  - Cálculo de las emisiones de CO₂ por usuario y por categoría.
  - Red social: seguir a otros usuarios, seguidores/seguidos, logros y búsqueda de amigos.
  - Suscripción a los **canales** de las empresas y lectura de sus publicaciones.

- **Panel de gestión (administradores de KarbonZero):**
  - Catálogos globales: medidas y unidades, tipos de vehículo, tamaños y combustibles, alimentos y categorías, servicios y sus unidades, tipos de vivienda, tipos de mascota, transportes y países.
  - Gestión de usuarios y administradores (alta, edición, deshabilitar).
  - Gestión de **empresas**: dominio de su backend/frontend, **API Key** y canal asociado.

- **APIs institucionales (otras empresas):**
  - Autenticación de la empresa (`/apiAuth/authenticateCompany`) mediante su API Key.
  - Lectura/actualización del canal de la empresa y aprobación de publicaciones.
  - Datos para el mapa de emisiones (GeoJSON de los países de Centroamérica y emisiones de CO₂ agrupadas por ubicación).

---

## Ecosistema KarbonZero

| Repositorio | Función | Consume |
|---|---|---|
| **KarbonZero_BackendMobile** _(este)_ | API central: app móvil, panel de gestión, empresas y canales | Oracle Database (`Database/KarbonZero.sql`) |
| [KarbonZero_Backend](https://github.com/Tato0408/KarbonZero_Backend) | API institucional de una empresa (consumo de recursos y CO₂) | Su propia BD (`Database/KarbonZeroInstitucional.sql`) + esta API (`EXTERNAL_ADMIN_DOMAIN`) |
| [KarbonZero_Frontend](https://github.com/Tato0408/KarbonZero_Frontend) | Panel web institucional de la empresa | API institucional + esta API (mapa) |
| [KarbonZeroManagment_Frontend](https://github.com/Tato0408/KarbonZeroManagment_Frontend) | Panel web de gestión (administradores) | Esta API |
| [KarbonZero_FrontendMobile](https://github.com/Tato0408/KarbonZero_FrontendMobile) | App móvil del usuario final (Framework7 + Capacitor) | Esta API |

### Ejecución local

Ninguno de los proyectos tiene actualmente un despliegue público activo: el ecosistema completo está preparado para ejecutarse en local. Puertos por defecto:

| Servicio | Repositorio | URL local |
|---|---|---|
| Base de datos (Oracle XE, `XEPDB1`) | Scripts en `KarbonZero_BackendMobile/Database` y `KarbonZero_Backend/Database` | `localhost:1521` |
| API central | KarbonZero_BackendMobile | http://localhost:8080 |
| API institucional | KarbonZero_Backend | http://localhost:8081 |
| Panel de gestión | KarbonZeroManagment_Frontend | http://localhost:5173 |
| Panel institucional | KarbonZero_Frontend | http://localhost:5174 |
| App móvil (navegador) | KarbonZero_FrontendMobile | http://localhost:5175 |

Orden de arranque: **base de datos → API central → API institucional → frontends**.

Cuentas de demostración incluidas en los scripts (contraseña `KarbonZero2026!` en todas):

| Dónde | Correo | Permisos |
|---|---|---|
| Panel de gestión | `gerardoajf@karbonzero.local` | Administrador (`status` 1) |
| App móvil | `freddy@karbonzero.local` | Usuario de la app |
| Panel institucional | `freddy@karbonzero.local` | Lectura y escritura (rol *Coordinador de finanzas*) |
| Panel institucional | `tato@karbonzero.local` | Solo lectura (rol *Coordinador de medio ambiente*) |

---

## Autores

| # | Autor | GitHub |
|---|-------|--------|
| 1 | Oscar Abel Velásquez Joyar | [@OscarAVJ](https://github.com/OscarAVJ) |
| 2 | Camila Mariana Quinteros Gómez | [@camila-mariana](https://github.com/Camila-mariana) |
| 3 | Freddy Ricardo Pérez Alvarenga | [@Tato0408](https://github.com/Tato0408) |
| 4 | Joshua Gilberto Marinero Hernández | [@Joshuaz](https://github.com/Joshuaz) |
| 5 | Gerardo Andrés Jovel Franco | [@GerardoAJF](https://github.com/GerardoAJF) |
| 6 | Edwin Geovanni Alfaro Alfaro | [@EdwinAlfaro123](https://github.com/EdwinAlfaro123) |

---

## Stack Tecnológico

| Capa | Tecnologías |
|------|-------------|
| **Lenguaje y framework** | Java 17, Spring Boot 3.4.7 (Web, Data JPA, Security, Validation, Mail) |
| **Base de datos** | Oracle Database (XE 21c en local) vía `ojdbc11` |
| **Autenticación** | JWT (`jjwt`) en cookie `authToken`, contraseñas con Argon2 |
| **Archivos** | Cloudinary (imágenes de perfil y publicaciones) |
| **Despliegue** | Ejecución local; preparado para Heroku (empaquetado `.war`, ver `Procfile`) |

---

## Requisitos previos

| Herramienta | Para qué |
|---|---|
| JDK 17 | Compilar y ejecutar el proyecto |
| Maven 3.9+ _(o el wrapper `mvnw` incluido)_ | Gestión de dependencias y build |
| Oracle Database XE 21c (o superior) | Base de datos local — ver [Base de datos](#base-de-datos) |
| Cuenta de Gmail con contraseña de aplicación y cuenta de Cloudinary _(opcionales)_ | Envío de correos de recuperación y subida de imágenes |
| IntelliJ IDEA / VS Code con extensión de Java _(recomendado)_ | Desarrollo |

---

## Arquitectura del Proyecto

```
KarbonZero_BackendMobile/
├── src/main/java/api/karbonzeromovil/
│   ├── Config/
│   │   ├── Security/SecurityConfig.java    # Rutas públicas y protegidas, filtro JWT
│   │   ├── Cors/CorsConfig.java            # Orígenes permitidos (incluye los dominios de las empresas registradas)
│   │   ├── Cloudinary/CloudinaryConfig.java
│   │   └── Argon2PasswordEncoder.java      # Hash de contraseñas
│   ├── Controllers/                        # Un controlador por dominio: Auth, Car, Company, Food, LivingSpace,
│   │                                       # Map, Measure, Pet, Service, Transport, User, Cloudinary
│   ├── Services/                           # Lógica de negocio (incluye Mail y MapService)
│   ├── Repositories/                       # Interfaces Spring Data JPA, una por entidad
│   ├── Entities/                           # Entidades JPA agrupadas por dominio (tablas TB*)
│   ├── Models/DTO/                         # DTOs agrupados por dominio
│   ├── Exceptions/                         # Excepciones personalizadas (NotFound, DuplicateData, ...)
│   ├── Utils/                              # JWTUtils, JwtCookieAuthFilter, CodeGenerator, Responses
│   ├── KarbonzeromovilApplication.java     # Punto de entrada (lee PORT en Heroku)
│   └── ServletInitializer.java
├── src/main/resources/
│   ├── application.properties              # Configuración de BD, JWT y correo
│   └── GeoJson/                            # Mapas de Costa Rica, El Salvador, Guatemala, Honduras, Nicaragua y Panamá
├── pom.xml
└── Procfile                                # Comando de arranque en Heroku
```

> **Nota:** el proyecto sigue una **arquitectura en capas** (`Controller → Service → Repository → Entity`), y dentro de cada capa los archivos se agrupan por **dominio de negocio** (`Car/`, `Food/`, `Company/`, …), de modo que una misma carpeta de dominio aparece en todas las capas.

---

## Librerías principales y su función

| Librería | Uso |
|----------|-----|
| `spring-boot-starter-web` | Servidor HTTP y controladores REST |
| `spring-boot-starter-data-jpa` | ORM (Hibernate) y repositorios |
| `ojdbc11` | Driver JDBC de Oracle |
| `spring-boot-starter-security` | Protección de rutas y roles |
| `jjwt-api` / `jjwt-impl` / `jjwt-jackson` | Generación y validación de tokens JWT |
| `argon2-jvm` | Hash seguro de contraseñas |
| `spring-boot-starter-validation` | Validación de DTOs (`@NotBlank`, `@Email`, …) |
| `spring-boot-starter-mail` | Envío del código de recuperación de contraseña |
| `cloudinary-http5` / `cloudinary-taglib` | Subida de imágenes a Cloudinary |
| `lombok` | Getters, setters y constructores generados |
| `spring-boot-starter-tomcat` / `maven-war-plugin` | Empaquetado como `.war` para el despliegue |

---

## Convenciones de nomenclatura

| Convención | Dónde se usa |
|---|---|
| `PascalCase` + sufijo de capa | Clases: `CarController`, `CarService`, `CarRepository`, `CarEntity`, `CarDTO`. |
| `camelCase` | Métodos, variables y rutas de endpoints (`/getAllCarSize`, `/insertFuelConsumption`). |
| Prefijo `api` + dominio | Prefijo de cada controlador (`/apiCar`, `/apiFood`, `/apiUser`). |
| Verbo CRUD al inicio | `getAll…`, `get…ById/{id}`, `insert…`, `update…/{id}`, `delete…/{id}`. |
| Sufijo `SP` | Endpoints paginados (`/getAllFuelTypeSP`, `/getAllCompaniesSP`). |
| `UPPER_CASE` con prefijo `TB` | Tablas de Oracle (`TBUSERS`, `TBCARS`, `TBUSERACHIEVEMENTS`) y sus columnas (`IDUSER`, `ACHIEVEMENTNAME`). |

---

## Endpoints de la API

La autenticación usa un **JWT guardado en la cookie `authToken`**. El token incluye el `status` del usuario, que se traduce en el rol `ROLE_<status>` de Spring Security.

### 1. Autenticación (`/apiAuth`)
- `POST /login` — inicio de sesión _(público)_
- `POST /logout` — cierra sesión _(público)_
- `GET /me` — usuario autenticado
- `POST /forgotPassword`, `POST /verifyCode` — recuperación de contraseña por código _(público)_
- `POST /authenticateCompany` — autenticación de una empresa con su API Key _(público)_
- `GET /company` — datos de la empresa autenticada

### 2. Consumos y catálogos

| Prefijo de ruta | Descripción |
|---|---|
| `/apiCar` | Vehículos, tipos, tamaños, combustibles y consumo de combustible; `getAllC02CarsByUser/{id}` |
| `/apiTransport` | Transportes y consumos de transporte; `getAllC02TransportByUser/{id}` |
| `/apiFood` | Alimentos, categorías y consumos de alimentos; `getAllC02FoodByUser/{id}` |
| `/apiService` | Servicios, unidades de servicio y consumos; `getAllC02ServiceByUser/{id}` |
| `/apiLivingSpace` | Viviendas y tipos de vivienda |
| `/apiPet` | Mascotas del usuario y tipos de mascota |
| `/apiMeasure` | Medidas y unidades de medida |
| `/apiMap/getCountryGeo/{name}` | GeoJSON de un país _(público)_ |
| `/api…/get…ConsumptionsCO2WithLocation` | Emisiones agrupadas por ubicación, usadas por el mapa _(públicos)_ |

### 3. Usuarios y red social (`/apiUser`)
- CRUD de usuarios y administradores, `updateDisabled/{id}`, `searchUsers/{variable}`
- `POST /insertUser`, `POST /confirmPassword`, `PUT /updateUserPassword/{id}` _(públicos — registro y recuperación)_
- Amigos: `getAllFriends`, `insertFriend`, `getFriendsByMainUser/{id}`, `getCountAllFollowersByUser/{id}`, `getExistRelation/{idUser1}/{idUser2}`
- Logros: `getAllAchievements`, `getAllAchievementsByUser/{id}`
- Países: `getAllCountries`, `getAllCountriesSP`, `insertCountry`
- Consumos del usuario: `getAllTransportConsumptionsByUser/{id}`, `getAllUserFoodConsumptions/{id}`, `getAllUserServiceConsumption/{id}`, `getAllCarsByUser/{id}`, `getAllPetsByUser/{id}`, `getCountUserConsumptions/{id}`

### 4. Empresas y canales (`/apiCompany`)
- CRUD de empresas, canales, publicaciones (`ChannelPost`) y suscripciones (`ChannelUser`)
- `GET /getApiKey/{id}`, `GET /getAllCompaniesDomains`
- `GET /getChannelByApiKey/{apiKey}`, `PUT /updateChannelByApiKey/{apiKey}` — usados por las APIs institucionales
- `GET /getChannelPostApproved/{apiKey}`, `GET /getChannelPostNoApproved/{apiKey}`, `PUT /approvePost/{id}`

### 5. Imágenes (`/api/image`)
- `POST /upload`, `POST /upload-to-folder` — subida a Cloudinary

---

## Base de datos

El repositorio incluye el script completo de la base de datos en [`Database/KarbonZero.sql`](Database/KarbonZero.sql). Se publica **con fines educativos y de referencia**: permite estudiar el modelo de datos del proyecto y levantar el ecosistema completo en un entorno local, sin depender de la infraestructura en la nube que se utilizó durante su desarrollo.

El script contiene la estructura completa y un conjunto de **datos de demostración**. Los usuarios de ejemplo usan correos ficticios (`@karbonzero.local`) y la misma contraseña de prueba (`KarbonZero2026!`); no incluye datos personales reales ni credenciales de ningún servidor.

| Contenido | Detalle |
|---|---|
| Tablas | 28 tablas (`TB*`) con llaves primarias, foráneas y restricciones `UNIQUE` y `CHECK` |
| Funciones y procedimientos | 18 funciones auxiliares (`get_id…_from_name`, `user_has_achievement`) y 2 procedimientos (`insert_car`, `insert_company`) |
| Datos de demostración | Catálogos (medidas, unidades, servicios, combustibles, tamaños de vehículo, transportes, tipos de vivienda y de mascota, alimentos y categorías, países), 6 usuarios, 3 empresas con sus canales y publicaciones, y consumos de ejemplo. La empresa **Ricaldone** está registrada con la API institucional local (`http://localhost:8081`) |

Para crearla en Oracle Database XE (21c o superior):

1. Como administrador (`SYSTEM` o `/ as sysdba`), crea el usuario de la API en la base conectable `XEPDB1`:

   ```sql
   ALTER SESSION SET CONTAINER = XEPDB1;
   CREATE USER KARBONZERO IDENTIFIED BY "tu_contraseña" DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
   GRANT CREATE SESSION, CREATE TABLE, CREATE PROCEDURE, CREATE VIEW, CREATE SEQUENCE TO KARBONZERO;
   ```

2. Conectado con ese usuario, ejecuta el script completo (en SQL Developer: abrir el archivo y **F5 — Ejecutar script**):

   ```bash
   sqlplus KARBONZERO/"tu_contraseña"@//localhost:1521/XEPDB1 @Database/KarbonZero.sql
   ```

3. Coloca ese usuario y contraseña en `DB_USER` / `DB_PASSWORD` del `.env` (ver [Variables de entorno](#variables-de-entorno)).

---

## Variables de entorno

Ninguna credencial está escrita en el código: `application.properties` solo hace referencia a variables (`${DB_PASSWORD}`, `${SECRET_KEY_JWT}`, …). En local se leen de un archivo **`.env`** en la raíz del proyecto (cargado con `spring.config.import`, ya está en `.gitignore`); en Heroku se leen de las **Config Vars**, que tienen prioridad sobre el `.env`.

| Variable | Obligatoria | Descripción |
|----------|:---:|-------------|
| `DB_URL` / `DB_USER` / `DB_PASSWORD` | Sí | Conexión a la base de datos Oracle |
| `DB_DRIVER` | No _(default `oracle.jdbc.OracleDriver`)_ | Driver JDBC |
| `SECRET_KEY_JWT` | Sí | Clave para firmar los tokens JWT |
| `SECURITY_JWT_ISSUER` / `JWT_EXPIRATION` | No _(default `karbonZeroManagement` / `86400000`)_ | Emisor y duración (ms) del JWT |
| `COOKIE_DOMAIN` | No _(vacío en local)_ | Dominio de la cookie de sesión; en producción, el dominio público de esta API |
| `MAIL_USERNAME` / `MAIL_PASSWORD` | Sí | Cuenta de Gmail y contraseña de aplicación para enviar correos |
| `CLOUDINARY_CLOUD_NAME` / `CLOUDINARY_API_KEY` / `CLOUDINARY_API_SECRET` | Para imágenes | Credenciales de Cloudinary |
| `PORT` | No | Puerto asignado por Heroku; si existe, la app lo usa automáticamente |

Para empezar, copia la plantilla y completa los valores:

```bash
cp .env.example .env
```

En Heroku: **Settings → Config Vars** (o `heroku config:set DB_URL=... DB_PASSWORD=...`).

---

## Ejecución del Proyecto

Requiere la base de datos creada (ver [Base de datos](#base-de-datos)) y el archivo `.env` configurado.

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / Mac
./mvnw spring-boot:run      # http://localhost:8080
```

Generar el `.war` para producción:

```bash
./mvnw clean package        # genera target/karbonzeromovil-0.0.1-SNAPSHOT.war
```

---

## Solución de problemas comunes

| Síntoma | Causa | Solución |
|---|---|---|
| `IO Error: The Network Adapter could not establish the connection` | Oracle XE no está corriendo | Inicia los servicios `OracleServiceXE` y `OracleOraDB21Home*TNSListener` (en `services.msc`) |
| `ORA-12514` (servicio no registrado) | `DB_URL` apunta a un servicio que no existe | Usa `jdbc:oracle:thin:@//localhost:1521/XEPDB1` |
| `ORA-01017` (usuario/contraseña inválidos) | Datos de `DB_USER` / `DB_PASSWORD` incorrectos | Verifica el usuario creado en el paso 1 de [Base de datos](#base-de-datos) |
| Compila con muchos errores `cannot find symbol` en getters | Lombok no se procesa con la versión de Java usada | Compila con JDK 17 (`JAVA_HOME` apuntando al JDK 17) |
| Todas las peticiones responden `401` / `403` | La cookie `authToken` no llega (falta `credentials: "include"` en el frontend, o `COOKIE_DOMAIN` no coincide con el dominio de la API) o el rol no tiene permiso | Revisa el `fetch` del cliente, deja `COOKIE_DOMAIN` vacío en local y revisa las reglas de `SecurityConfig.java` |
| Error de CORS desde un frontend | El origen no es `localhost`, no está en `CorsConfig.java` ni está registrado como dominio de una empresa | En local se acepta `http://localhost` en cualquier puerto; para otros orígenes agrégalos en `CorsConfig.java` o registra el `FRONTENDDOMAIN` de la empresa |
| `Could not resolve placeholder 'DB_URL'` al arrancar | No existe el `.env` o falta esa variable | Crea el `.env` a partir de `.env.example` (o define la Config Var en Heroku) |
| Falla la subida de imágenes | Faltan las variables `CLOUDINARY_*` | Agrégalas al `.env` o a las Config Vars |
| No llega el código de recuperación | Contraseña de aplicación de Gmail inválida o revocada | Genera una nueva contraseña de aplicación y actualiza `MAIL_PASSWORD` |

---

Este proyecto es de uso académico. Todos los derechos reservados.
