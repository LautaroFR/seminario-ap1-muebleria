-- =====================================================
-- Base de Datos
-- Sistema de Gestión de Pedidos y Producción
-- Mueblería
-- =====================================================

DROP DATABASE IF EXISTS muebleria;

CREATE DATABASE muebleria;

USE muebleria;

-- =====================================================
-- Tabla CLIENTE
-- =====================================================

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(100),
    direccion VARCHAR(150)
);

-- =====================================================
-- Tabla PEDIDO
-- =====================================================

CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha DATE NOT NULL,
    descripcion VARCHAR(255),
    precio_total DECIMAL(12,2),
    estado VARCHAR(30),
    fecha_entrega DATE,

    CONSTRAINT fk_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES cliente(id_cliente)
);

-- =====================================================
-- Tabla PAGO
-- =====================================================

CREATE TABLE pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    fecha DATE,
    importe DECIMAL(12,2),

    CONSTRAINT fk_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES pedido(id_pedido)
);

-- =====================================================
-- INSERTS DE PRUEBA
-- =====================================================

INSERT INTO cliente
(nombre, telefono, email, direccion)
VALUES
('Juan Pérez','11-45678910','juan@gmail.com','Av. Rivadavia 1200'),
('María Gómez','11-43211234','maria@gmail.com','Mitre 350');

INSERT INTO pedido
(id_cliente, fecha, descripcion, precio_total, estado, fecha_entrega)
VALUES
(1,'2026-09-10','Placard de melamina',950000,'En producción',NULL),
(2,'2026-09-15','Mueble de TV',1200000,'Confirmado',NULL);

INSERT INTO pago
(id_pedido, fecha, importe)
VALUES
(1,'2026-09-10',300000),
(1,'2026-09-18',200000),
(2,'2026-09-15',500000);

-- =====================================================
-- CONSULTAS
-- =====================================================

-- Todos los clientes
SELECT * FROM cliente;

-- Todos los pedidos
SELECT * FROM pedido;

-- Pedidos con su cliente
SELECT
    c.nombre,
    p.descripcion,
    p.estado
FROM cliente c
INNER JOIN pedido p
ON c.id_cliente = p.id_cliente;

-- Pagos registrados
SELECT
    p.id_pedido,
    pa.fecha,
    pa.importe
FROM pedido p
INNER JOIN pago pa
ON p.id_pedido = pa.id_pedido;

-- Saldo pendiente
SELECT
    p.id_pedido,
    p.precio_total,
    SUM(pa.importe) AS abonado,
    p.precio_total - SUM(pa.importe) AS saldo
FROM pedido p
INNER JOIN pago pa
ON p.id_pedido = pa.id_pedido
GROUP BY p.id_pedido;

-- Pedidos en producción
SELECT *
FROM pedido
WHERE estado = 'En producción';

-- Clientes ordenados
SELECT *
FROM cliente
ORDER BY nombre;

-- =====================================================
-- ELIMINACIÓN DE REGISTROS
-- =====================================================

DELETE FROM pago
WHERE id_pago = 3;

DELETE FROM cliente
WHERE id_cliente = 2;