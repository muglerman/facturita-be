# Datos Iniciales para Sistema Multitenant - FacturitaX

## Resumen

Este documento explica cómo configurar y utilizar datos iniciales en el sistema FacturitaX para evitar tener que registrar usuarios manualmente en cada ambiente.

## Estrategias Implementadas

### 1. **DataLoader Básico** (`DataLoader.java`)
- Crea un usuario administrador automáticamente al iniciar la aplicación
- Solo se ejecuta si no existen usuarios en el sistema
- **Credenciales por defecto:**
  - Email: `admin@facturita.com`
  - Contraseña: `admin123`

### 2. **DataLoader Multitenant** (`MultiTenantDataLoader.java`)
- Crea empresas y usuarios de demostración para testing
- Se ejecuta después del DataLoader básico
- **Empresas creadas:**
  - Tech Solutions S.A.C. (RUC: 20123456789)
  - Restaurante El Buen Sabor E.I.R.L. (RUC: 20987654321)
  - Comercial Los Andes S.R.L. (RUC: 20456789123)

- **Usuarios creados:**
  - `gerente@techsolutions.com` / `tech123` (Esquema: cna)
  - `admin@restaurante.com` / `resto123` (Esquema: restaurante)
  - `supervisor@comercial.com` / `comercial123` (Esquema: comercial)

### 3. **PlanDataLoader** (`PlanDataLoader.java`)
- Crea planes de suscripción de demostración para testing
- Se ejecuta como segundo en el orden (Order 2)
- **Planes creados:**
  - **Gratuito** - $0.00 - 1 usuario, 10 docs, solo notas de venta
  - **Básico** - $29.90 - 1 usuario, 100 docs, boletas y notas
  - **Profesional** - $59.90 - 3 usuarios, 500 docs, incluye facturas
  - **Empresarial** - $99.90 - 10 usuarios, 2000 docs, documentos completos
  - **Premium** - $199.90 - 25 usuarios, 10000 docs, ilimitado
  - **Plan Descontinuado** - $49.90 - BLOQUEADO (para testing)

## **Sistema Multitenant por Subdominio**

El sistema está configurado para manejar múltiples tenants usando subdominios:

### **Resolución de Tenants:**
- **localhost:8080** → Esquema: `admin` (Desarrollo)
- **facturita.com** → Esquema: `admin` (Producción)
- **cna.facturita.com** → Esquema: `cna`
- **empresa1.facturita.com** → Esquema: `empresa1`

### **Configuración de Base de Datos:**
- Cada tenant tiene su propio esquema en PostgreSQL
- Los datos se aíslan por esquema usando `currentSchema` en la URL de conexión
- El esquema `admin` contiene los datos del proveedor del sistema

### 4. **Script SQL** (`data-multitenant.sql`)
- Alternativa usando SQL puro
- Se puede ejecutar automáticamente con Spring Boot
- Incluye más datos de demostración y configuraciones

## Cómo Usar

### Opción 1: DataLoaders (Recomendado para desarrollo)

Los DataLoaders se ejecutan automáticamente al iniciar la aplicación. No necesitas hacer nada adicional.

```bash
# Iniciar la aplicación
mvn spring-boot:run

# Los logs mostrarán:
# [DataLoader] Usuario administrador creado: admin@facturita.com
# [MultiTenantDataLoader] Empresas demo creadas...
```

### Opción 2: Script SQL (Para environments específicos)

Si quieres usar el script SQL en lugar de los DataLoaders:

1. **Deshabilitar DataLoaders:**
```java
// Comentar o eliminar las anotaciones @Component de los DataLoaders
// @Component
public class DataLoader implements CommandLineRunner {
```

2. **Configurar en application.properties:**
```properties
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data-multitenant.sql
```

### Opción 3: Configuración por Perfil

Crear perfiles específicos para diferentes ambientes:

**application-dev.properties:**
```properties
# Habilitar datos de demostración en desarrollo
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data-multitenant.sql
```

**application-prod.properties:**
```properties
# Deshabilitar datos de demostración en producción
spring.sql.init.mode=never
```

## Usuarios Disponibles Después de la Carga

| Email | Contraseña | Tipo | Empresa/Contexto |
|-------|------------|------|------------------|
| `admin@facturita.com` | `admin123` | Administrador | Sistema |
| `gerente@techsolutions.com` | `tech123` | Usuario | Tech Solutions |
| `admin@restaurante.com` | `resto123` | Usuario | Restaurante |
| `supervisor@comercial.com` | `comercial123` | Usuario | Comercial |

## Planes Disponibles Después de la Carga

| Nombre | Precio | Usuarios | Documentos | Estado | Características |
|--------|--------|----------|------------|--------|-----------------|
| Gratuito | $0.00 | 1 | 10 | Activo | Solo notas de venta |
| Básico | $29.90 | 1 | 100 | Activo | Boletas y notas |
| Profesional | $59.90 | 3 | 500 | Activo | Incluye facturas |
| Empresarial | $99.90 | 10 | 2000 | Activo | Documentos completos |
| Premium | $199.90 | 25 | 10000 | Activo | Ilimitado |
| Plan Descontinuado | $49.90 | 2 | 200 | Bloqueado | Para testing |

## Configuraciones Adicionales

### Personalizar Datos Iniciales

Para personalizar los datos que se crean, edita los archivos:

1. **Para usuarios básicos:** `DataLoader.java`
2. **Para datos multitenant:** `MultiTenantDataLoader.java`
3. **Para datos SQL:** `data-multitenant.sql`

### Variables de Entorno para Producción

```bash
# Establecer credenciales seguras en producción
export ADMIN_DEFAULT_EMAIL="admin@tuempresa.com"
export ADMIN_DEFAULT_PASSWORD="contraseña-super-segura"
export JWT_SECRET="tu-secret-jwt-muy-largo-y-seguro"
```

### Verificar Datos Cargados

Después de iniciar la aplicación, verifica que los datos se cargaron correctamente:

```bash
# Verificar en logs de la aplicación
grep -i "DataLoader" application.log

# O hacer login con las credenciales por defecto
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@facturita.com","password":"admin123"}'
```

### Probar el PlanController

Una vez que tengas el token JWT del login, puedes probar los endpoints de planes:

```bash
# Obtener todos los planes
curl -X GET http://localhost:8080/api/planes \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Obtener solo planes activos
curl -X GET http://localhost:8080/api/planes/activos \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Obtener un plan específico
curl -X GET http://localhost:8080/api/planes/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Buscar planes
curl -X GET "http://localhost:8080/api/planes/buscar?q=básico" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Obtener estadísticas de planes
curl -X GET http://localhost:8080/api/planes/stats \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Crear un nuevo plan (solo ADMIN)
curl -X POST http://localhost:8080/api/planes \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Plan Personalizado",
    "precio": 79.90,
    "limiteUsuarios": 5,
    "limiteDocumentos": 1000,
    "aplicarLimiteDocsNotasVenta": false,
    "documentosDelPlan": ["boleta", "factura", "nota_venta"],
    "bloqueado": false,
    "aplicarLimiteVentasEnNotasVenta": false,
    "limiteDeVentas": 50000.00,
    "ventasIlimitadas": false,
    "limiteEstablecimientos": 3,
    "establecimientosIlimitados": false
  }'
```

## Seguridad y Mejores Prácticas

### ⚠️ Importante para Producción

1. **Cambiar contraseñas por defecto:**
   - NUNCA usar `admin123`, `demo123`, etc. en producción
   - Usar contraseñas fuertes y únicas

2. **Deshabilitar datos de demostración:**
   - Comentar o eliminar los DataLoaders en producción
   - No ejecutar scripts SQL con datos demo

3. **Variables de entorno:**
   - Usar variables de entorno para credenciales
   - No hardcodear contraseñas en el código

### Ejemplo de Configuración Segura

```java
@Value("${facturita.admin.default-email:admin@facturita.com}")
private String defaultAdminEmail;

@Value("${facturita.admin.default-password:}")
private String defaultAdminPassword;

private void crearUsuarioAdministrador() {
    if (defaultAdminPassword.isEmpty()) {
        log.warn("No se especificó contraseña de administrador. Saltando creación.");
        return;
    }
    // ... resto del código
}
```

## Troubleshooting

### Problema: Los datos no se cargan

**Solución 1:** Verificar que los DataLoaders están habilitados
```java
@Component // Esta anotación debe estar presente
@Order(1)   // El orden importa
public class DataLoader implements CommandLineRunner {
```

**Solución 2:** Verificar logs de la aplicación
```bash
grep -i "DataLoader\|CommandLineRunner" application.log
```

### Problema: Error de duplicado en base de datos

**Solución:** Los DataLoaders verifican si ya existen datos antes de crear nuevos. Si aún así hay problemas:

```sql
-- Limpiar datos existentes (CUIDADO: Solo en desarrollo)
DELETE FROM usuarios WHERE email LIKE '%@demo%' OR email = 'admin@facturita.com';
DELETE FROM empresas WHERE ruc IN ('20123456789', '20987654321', '20456789123');
```

### Problema: Contraseñas no funcionan

**Solución:** Verificar que el PasswordEncoder está configurado correctamente:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## Scripts Útiles

### Verificar usuarios creados:
```sql
SELECT id, nombre, email, fecha_verificacion_correo, fecha_de_creacion 
FROM usuarios 
ORDER BY fecha_de_creacion;
```

### Verificar empresas creadas:
```sql
SELECT ruc, razon_social 
FROM empresas 
ORDER BY razon_social;
```

### Resetear datos de demo (SOLO DESARROLLO):
```sql
DELETE FROM usuarios WHERE email != 'tu-admin-real@empresa.com';
DELETE FROM empresas WHERE ruc LIKE '20%';
```

---

## Conclusión

Con estas estrategias, ya no necesitas registrar usuarios manualmente cada vez que inicies el sistema. Los datos iniciales se cargan automáticamente y puedes hacer login inmediatamente para probar la aplicación.

**Recuerda:** Siempre cambiar las contraseñas por defecto en ambientes de producción.
