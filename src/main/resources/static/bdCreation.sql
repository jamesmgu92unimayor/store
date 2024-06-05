CREATE DATABASE store_example;

-- Crear la extensión para poder utilizar UUIDs
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- Crear la tabla de usuarios
CREATE TABLE usuarios
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre     VARCHAR(100)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255)        NOT NULL,
    tipo_usuario VARCHAR(100)
);

-- Tabla para los productos
CREATE TABLE producto
(
    id             UUID PRIMARY KEY        DEFAULT uuid_generate_v4(),
    nombre         VARCHAR(100)   NOT NULL,
    precio         DECIMAL(10, 2) NOT NULL DEFAULT 0,
    descripcion    VARCHAR(100),
    fecha_registro TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);

alter table producto add column cantidad INT default 0;

-- Usuario Admin ---
--contrasena admin123
INSERT INTO public.usuarios
    (id, nombre, email, contrasena)
VALUES ('f2320a65-5750-47e7-ae71-d4abd4d33b6c'::uuid, 'super admin', 'superadmin@yopmail.com',
        '$2a$10$C2tAXhiADUiY6rs.K3FLwOK2xCwKmxRU1noize7CNSnFj8pu2RY0e');

-- Crear las funciones en el esquema utilidades
CREATE OR REPLACE FUNCTION aumentar_cantidad_producto(p_id uuid, p_cantidad int)
RETURNS void AS $$
BEGIN
UPDATE public.producto
SET cantidad = cantidad + p_cantidad
WHERE id = p_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION disminuir_cantidad_producto(p_id uuid, p_cantidad int)
RETURNS void AS $$
BEGIN
UPDATE public.producto
SET cantidad = cantidad - p_cantidad
WHERE id = p_id;

-- Asegurarse de que la cantidad no sea negativa
IF (SELECT cantidad FROM public.producto WHERE id = p_id) < 0 THEN
        RAISE EXCEPTION 'La cantidad no puede ser negativa';
END IF;
END;
$$ LANGUAGE plpgsql;




