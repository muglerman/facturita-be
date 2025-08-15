-- Script de datos iniciales para Facturita (Multitenant)
-- Este archivo se puede colocar en src/main/resources/data.sql para ejecución automática


INSERT INTO usuarios (nombre, email, password, fecha_verificacion_correo, fecha_de_creacion, fecha_de_actualizacion)
SELECT 'Super Administrador', 'superadmin@facturita.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', NOW(), NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'superadmin@facturita.com');

-- ================================================
-- ESQUEMA DE TABLAS NECESARIAS
-- ================================================

CREATE TABLE IF NOT EXISTS empresas (
	id SERIAL PRIMARY KEY,
	ruc VARCHAR(20) UNIQUE NOT NULL,
	razon_social VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
	id SERIAL PRIMARY KEY,
	nombre VARCHAR(255) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	fecha_verificacion_correo TIMESTAMP,
	fecha_de_creacion TIMESTAMP,
	fecha_de_actualizacion TIMESTAMP
);

CREATE TABLE IF NOT EXISTS configuraciones (
	id SERIAL PRIMARY KEY,
	ambito VARCHAR(50) NOT NULL,
	grupo VARCHAR(50) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	valor VARCHAR(255),
	descripcion VARCHAR(255),
	activo BOOLEAN DEFAULT TRUE
);


INSERT INTO empresas (ruc, razon_social)
SELECT '20123456789', 'Tecnología Avanzada S.A.C.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20123456789');

INSERT INTO empresas (ruc, razon_social)
SELECT '20987654321', 'Restaurante Sabor Peruano E.I.R.L.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20987654321');

INSERT INTO empresas (ruc, razon_social)
SELECT '20456789123', 'Distribuidora Nacional S.R.L.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20456789123');

INSERT INTO empresas (ruc, razon_social)
SELECT '20789123456', 'Servicios Integrales del Sur S.A.C.'
WHERE NOT EXISTS (SELECT 1 FROM empresas WHERE ruc = '20789123456');


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


INSERT INTO configuraciones (ambito, grupo, nombre, valor, descripcion, activo)
SELECT 'GLOBAL', 'SISTEMA', 'VERSION', '1.0.0', 'Versión actual del sistema', true
WHERE NOT EXISTS (SELECT 1 FROM configuraciones WHERE ambito = 'GLOBAL' AND nombre = 'VERSION');

INSERT INTO configuraciones (ambito, grupo, nombre, valor, descripcion, activo)
SELECT 'GLOBAL', 'MULTITENANT', 'HABILITADO', 'true', 'Indica si el multitenant está habilitado', true
WHERE NOT EXISTS (SELECT 1 FROM configuraciones WHERE ambito = 'GLOBAL' AND nombre = 'HABILITADO');

INSERT INTO configuraciones (ambito, grupo, nombre, valor, descripcion, activo)
SELECT 'GLOBAL', 'AUTH', 'JWT_EXPIRATION_HOURS', '24', 'Horas de expiración del JWT', true
WHERE NOT EXISTS (SELECT 1 FROM configuraciones WHERE ambito = 'GLOBAL' AND nombre = 'JWT_EXPIRATION_HOURS');




