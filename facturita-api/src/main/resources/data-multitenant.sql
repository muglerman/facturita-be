-- Script de datos iniciales para Facturita (Multitenant)
-- Este archivo se puede colocar en src/main/resources/data.sql para ejecución automática

-- ================================================
-- USUARIOS INICIALES
-- ================================================

-- Usuario administrador del sistema (solo si no existe)
INSERT INTO usuarios (nombre, email, password, fecha_verificacion_correo, fecha_de_creacion, fecha_de_actualizacion)
SELECT 'Super Administrador', 'superadmin@facturita.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'superadmin@facturita.com');

-- ================================================
-- EMPRESAS DE DEMOSTRACIÓN
-- ================================================

-- Empresa 1: Tecnología
INSERT INTO empresas (ruc, razon_social)
SELECT '20123456789', 'Tecnología Avanzada S.A.C.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20123456789');

-- Empresa 2: Restaurante
INSERT INTO empresas (ruc, razon_social)
SELECT '20987654321', 'Restaurante Sabor Peruano E.I.R.L.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20987654321');

-- Empresa 3: Comercial
INSERT INTO empresas (ruc, razon_social)
SELECT '20456789123', 'Distribuidora Nacional S.R.L.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20456789123');

-- Empresa 4: Servicios
INSERT INTO empresas (ruc, razon_social)
SELECT '20789123456', 'Servicios Integrales del Sur S.A.C.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20789123456');

-- ================================================
-- USUARIOS POR TENANT/EMPRESA
-- ================================================

-- Usuarios para diferentes empresas (multitenant)
INSERT INTO usuarios (nombre, email, password, fecha_verificacion_correo, fecha_de_creacion, fecha_de_actualizacion)
SELECT 'Gerente Tecnología', 'gerente@tecnologia.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'gerente@tecnologia.com');

INSERT INTO usuarios (nombre, email, password, fecha_verificacion_correo, fecha_de_creacion, fecha_de_actualizacion)
SELECT 'Administrador Restaurante', 'admin@restaurante.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@restaurante.com');

INSERT INTO usuarios (nombre, email, password, fecha_verificacion_correo, fecha_de_creacion, fecha_de_actualizacion)
SELECT 'Supervisor Comercial', 'supervisor@comercial.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'supervisor@comercial.com');

INSERT INTO usuarios (nombre, email, password, fecha_verificacion_correo, fecha_de_creacion, fecha_de_actualizacion)
SELECT 'Contador Servicios', 'contador@servicios.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'contador@servicios.com');

-- ================================================
-- CONFIGURACIONES INICIALES (si existe la tabla)
-- ================================================

-- Configuraciones globales del sistema
INSERT INTO configuraciones (ambito, grupo, nombre, valor, descripcion, activo)
SELECT 'GLOBAL', 'SISTEMA', 'VERSION', '1.0.0', 'Versión actual del sistema', true
WHERE NOT EXISTS (SELECT 1 FROM configuraciones WHERE ambito = 'GLOBAL' AND nombre = 'VERSION');

INSERT INTO configuraciones (ambito, grupo, nombre, valor, descripcion, activo)
SELECT 'GLOBAL', 'MULTITENANT', 'HABILITADO', 'true', 'Indica si el multitenant está habilitado', true
WHERE NOT EXISTS (SELECT 1 FROM configuraciones WHERE ambito = 'GLOBAL' AND nombre = 'HABILITADO');

INSERT INTO configuraciones (ambito, grupo, nombre, valor, descripcion, activo)
SELECT 'GLOBAL', 'AUTH', 'JWT_EXPIRATION_HOURS', '24', 'Horas de expiración del JWT', true
WHERE NOT EXISTS (SELECT 1 FROM configuraciones WHERE ambito = 'GLOBAL' AND nombre = 'JWT_EXPIRATION_HOURS');

-- ================================================
-- NOTAS IMPORTANTES
-- ================================================

-- Contraseña por defecto para todos los usuarios: "password123"
-- Hash BCrypt de "password123": $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi
-- 
-- IMPORTANTE: 
-- 1. Cambiar todas las contraseñas en producción
-- 2. Los usuarios creados tienen verificación de correo automática (fecha_verificacion_correo = NOW())
-- 3. Este script usa INSERT con WHERE NOT EXISTS para evitar duplicados
-- 4. Se recomienda ejecutar este script solo en desarrollo y testing

-- ================================================
-- INFORMACIÓN DE ACCESO PARA DESARROLLO
-- ================================================

-- Usuarios de prueba creados:
-- superadmin@facturita.com / password123 (Administrador general)
-- gerente@tecnologia.com / password123 (Tenant: Tecnología)
-- admin@restaurante.com / password123 (Tenant: Restaurante)
-- supervisor@comercial.com / password123 (Tenant: Comercial)
-- contador@servicios.com / password123 (Tenant: Servicios)
