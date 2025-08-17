-- DDL para entidades tenant
CREATE SCHEMA IF NOT EXISTS ${tenant};

CREATE TABLE IF NOT EXISTS ${tenant}.t_tipos_documento_identidad (
    id VARCHAR(2) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ${tenant}.t_departamentos (
    id VARCHAR(2) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ${tenant}.t_provincias (
    id VARCHAR(4) PRIMARY KEY,
    departamento_id VARCHAR(2) NOT NULL REFERENCES ${tenant}.t_departamentos(id),
    nombre VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ${tenant}.t_distritos (
    id VARCHAR(6) PRIMARY KEY,
    provincia_id VARCHAR(4) NOT NULL REFERENCES ${tenant}.t_provincias(id),
    nombre VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ${tenant}.t_paises (
    id VARCHAR(2) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ${tenant}.t_clientes (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo_documento_identidad_id VARCHAR(2) NOT NULL REFERENCES ${tenant}.t_tipos_documento_identidad(id),
    numero VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    nombre_comercial VARCHAR(255),
    pais_id VARCHAR(2) NOT NULL REFERENCES ${tenant}.t_paises(id),
    distrito_id VARCHAR(6) REFERENCES ${tenant}.t_distritos(id),
    direccion VARCHAR(255),
    condicion_sunat VARCHAR(255),
    estado_sunat VARCHAR(255),
    estado BOOLEAN NOT NULL,
    email VARCHAR(255) UNIQUE,
    telefono VARCHAR(255),
    observacion TEXT,
    fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ${tenant}.t_usuarios (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    fecha_verificacion_email TIMESTAMP,
    password VARCHAR(255) NOT NULL,
    establecimiento_id INTEGER,
    tipo_usuario VARCHAR(20) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    bloqueado BOOLEAN NOT NULL DEFAULT FALSE
);