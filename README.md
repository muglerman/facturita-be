# FacturitaX - Sistema Multitenant de Facturación Electrónica

## Descripción General
FacturitaX es una plataforma de facturación electrónica multitenant desarrollada en Java 21 y Spring Boot 3.5.4. Permite gestionar múltiples empresas (tenants) bajo un mismo sistema, aislando datos por esquema en PostgreSQL y facilitando la administración, autenticación y emisión de comprobantes electrónicos.

## Estructura del Proyecto
Este repositorio es un proyecto Maven multi-módulo compuesto por los siguientes módulos:

- **facturita-core**: Lógica de negocio principal, modelos, servicios y repositorios.
- **facturita-dto**: Definición de DTOs, validaciones y utilidades para transferencia de datos.
- **facturita-config**: Configuración global, CORS, Swagger y utilidades de arranque.
- **facturita-security**: Seguridad, JWT, autenticación y configuración de usuarios.
- **facturita-integrations**: Integraciones externas (APIs, servicios de terceros).
- **facturita-multitenant**: Implementación multitenant por subdominio y gestión de contextos.
- **facturita-batch**: Procesos batch y tareas programadas.
- **facturita-api**: API REST principal, controladores y endpoints públicos/privados.

## Tecnologías
- **Java 21**
- **Spring Boot 3.5.4**
- **PostgreSQL** (multiesquema)
- **JWT** para autenticación
- **Swagger/OpenAPI** para documentación
- **Maven** como gestor de dependencias

## Configuración Multitenant
- Cada empresa (tenant) tiene su propio esquema en la base de datos.
- Resolución de tenant por subdominio (ej: `cna.facturita.com` → esquema `cna`).
- El esquema `admin` contiene datos del proveedor y administración global.
- Configuración de conexión usando `currentSchema` en la URL de PostgreSQL.

## Datos Iniciales
### Estrategias
- **DataLoader.java**: Crea usuario administrador si no existen usuarios.
- **MultiTenantDataLoader.java**: Crea empresas y usuarios demo para testing.
- **PlanDataLoader.java**: Crea planes de suscripción de ejemplo.
- **data-multitenant.sql**: Script SQL alternativo para carga masiva de datos demo.

#### Usuarios Demo
| Email                        | Contraseña     | Tipo         | Empresa/Contexto |
|------------------------------|---------------|--------------|------------------|
| admin@facturita.com          | admin123      | Administrador| Sistema          |
| gerente@techsolutions.com    | tech123       | Usuario      | Tech Solutions   |
| admin@restaurante.com        | resto123      | Usuario      | Restaurante      |
| supervisor@comercial.com     | comercial123  | Usuario      | Comercial        |

#### Planes Demo
| Nombre           | Precio   | Usuarios | Documentos | Estado     | Características         |
|------------------|---------|----------|------------|------------|-------------------------|
| Gratuito         | $0.00   | 1        | 10         | Activo     | Solo notas de venta     |
| Básico           | $29.90  | 1        | 100        | Activo     | Boletas y notas         |
| Profesional      | $59.90  | 3        | 500        | Activo     | Incluye facturas        |
| Empresarial      | $99.90  | 10       | 2000       | Activo     | Documentos completos    |
| Premium          | $199.90 | 25       | 10000      | Activo     | Ilimitado               |
| Plan Descontinuado| $49.90 | 2        | 200        | Bloqueado  | Para testing            |

## Configuración CORS
- Permite peticiones desde `http://localhost:4200`, `http://localhost:3000`, `http://127.0.0.1:4200`, `http://127.0.0.1:3000`.
- Integración con Spring Security y anotaciones `@CrossOrigin` en controladores clave.

## Endpoints Principales
- **Registro de usuario:** `POST /auth/registro`
- **Login:** `POST /auth/login`
- **Verificar usuarios existentes:** `GET /auth/tiene-usuarios`
- **Gestión de usuarios:** `GET /usuarios` (requiere JWT)

## Ejemplo de Flujo Frontend
1. Verificar si hay usuarios: `GET /auth/tiene-usuarios`
2. Si no hay usuarios, registrar el primero: `POST /auth/registro`
3. Hacer login: `POST /auth/login`
4. Usar el token JWT para peticiones autenticadas

## Personalización y Variables de Entorno
- Edita los DataLoaders o el script SQL para personalizar datos iniciales.
- Variables recomendadas para producción:
  ```bash
  export ADMIN_DEFAULT_EMAIL="admin@tuempresa.com"
  export ADMIN_DEFAULT_PASSWORD="contraseña-super-segura"
  export JWT_SECRET="tu-secret-jwt-muy-largo-y-seguro"
  ```

## Ejecución del Proyecto
1. Clona el repositorio:
   ```bash
   git clone https://github.com/muglerman/facturita-be.git
   cd facturita-be
   ```
2. Compila todos los módulos:
   ```bash
   mvn clean install
   ```
3. Inicia el API principal:
   ```bash
   cd facturita-api
   mvn spring-boot:run
   ```
4. Accede a la documentación Swagger en `http://localhost:8080/swagger-ui.html`

## Perfiles y Configuración de Datos
- **Desarrollo:** Habilita datos demo en `application-dev.properties`.
- **Producción:** Deshabilita datos demo en `application-prod.properties`.

## Contribución
1. Realiza un fork y crea una rama para tu feature.
2. Haz tus cambios y envía un pull request.
3. Sigue las convenciones de código y documentación.

## Licencia
Este proyecto está bajo la licencia MIT.

## Contacto
- Autor: muglerman
- Email: admin@facturita.com
- GitHub: [muglerman/facturita-be](https://github.com/muglerman/facturita-be)
