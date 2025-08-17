-- V1__init_admin.sql
CREATE SCHEMA IF NOT EXISTS admin;
SET search_path TO admin;

CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fecha_verificacion_correo TIMESTAMP,
    recordar_token VARCHAR(255),
    fecha_de_creacion TIMESTAMP,
    fecha_de_actualizacion TIMESTAMP
);
