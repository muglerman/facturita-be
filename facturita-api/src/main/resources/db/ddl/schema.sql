-- DDL para entidades administrativas
-- Tabla de configuraciones
CREATE TABLE IF NOT EXISTS ${tenant}.configuraciones (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    admin_bloqueado BOOLEAN NOT NULL,
    certificado VARCHAR(255),
    envio_soap_id VARCHAR(2),
    tipo_soap_id VARCHAR(2),
    soap_username VARCHAR(255),
    soap_password VARCHAR(255),
    soap_url VARCHAR(255),
    token_public_culqui TEXT,
    token_private_culqui TEXT,
    url_apiruc TEXT,
    token_apiruc TEXT,
    use_login_global BOOLEAN NOT NULL,
    login TEXT,
    whatsapp_habilitado BOOLEAN,
    regex_password_cliente BOOLEAN NOT NULL,
    fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS ${tenant}.usuarios (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    fecha_verificacion_correo TIMESTAMP,
    recordar_token VARCHAR(255),
    fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP
);

-- Tabla de planes
CREATE TABLE IF NOT EXISTS ${tenant}.planes (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255),
    precio DOUBLE PRECISION,
    limite_usuarios INT DEFAULT 0,
    limite_documentos INT DEFAULT 0,
    incluir_nota_venta_documentos BOOLEAN DEFAULT FALSE,
    documentos_plan JSON,
    habilitado BOOLEAN DEFAULT FALSE,
    incluir_nota_venta_ventas BOOLEAN DEFAULT FALSE,
    limite_ventas NUMERIC DEFAULT 0,
    limite_establecimientos INT DEFAULT 0,
    fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP
);
