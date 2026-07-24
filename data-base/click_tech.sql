-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 23-07-2026 a las 22:57:43
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS click_tech;
USE click_tech;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `click_tech`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id_categorias` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen` varchar(500) DEFAULT NULL,
  `activa` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id_categorias`, `nombre`, `descripcion`, `imagen`, `activa`) VALUES
(1, 'Teclados', 'Teclados mecánicos, membrana y ergonómicos', NULL, 1),
(2, 'Mouses', 'Mouses gaming, ergonómicos e inalámbricos', NULL, 1),
(3, 'Monitores', 'Monitores gaming, profesionales y ultrapanorámicos', NULL, 1),
(4, 'Audio', 'Audífonos, micrófonos y parlantes', NULL, 1),
(5, 'Accesorios', 'Webcams, mousepads, hubs y más', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_pedido`
--

CREATE TABLE `detalle_pedido` (
  `id_detalle` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` decimal(12,2) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_pedido`
--

INSERT INTO `detalle_pedido` (`id_detalle`, `id_pedido`, `id_producto`, `cantidad`, `precio_unitario`, `subtotal`) VALUES
(1, 1, 1, 1, 289900.00, 289900.00),
(2, 1, 2, 1, 179900.00, 179900.00),
(3, 2, 3, 1, 1149900.00, 1149900.00),
(4, 3, 4, 1, 349900.00, 349900.00),
(5, 3, 2, 1, 179900.00, 179900.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_pedido` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `total` decimal(12,2) NOT NULL,
  `metodo_pago` varchar(50) DEFAULT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id_pedido`, `id_usuario`, `direccion`, `total`, `metodo_pago`, `fecha`) VALUES
(1, 2, 'Cra 15 #45-20, Bogotá', 469800.00, 'Tarjeta de crédito', '2026-07-23 15:44:47'),
(2, 3, 'Cll 10 #25-30, Medellín', 1149900.00, 'PSE', '2026-07-23 15:44:47'),
(3, 4, 'Av 6N #23-45, Cali', 539800.00, 'Nequi', '2026-07-23 15:44:47'),
(4, 5, 'Cra 8 #12-50, Barranquilla', 289900.00, 'Daviplata', '2026-07-23 15:44:47'),
(5, 2, 'Cra 15 #45-20, Bogotá', 609800.00, 'Tarjeta de débito', '2026-07-23 15:44:47');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio` decimal(12,2) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `imagen` varchar(500) DEFAULT NULL,
  `id_categorias` int(11) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 1,
  `fecha_creacion` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `nombre`, `descripcion`, `precio`, `stock`, `imagen`, `id_categorias`, `activo`, `fecha_creacion`) VALUES
(1, 'Teclado Mecánico RGB Pro', 'Teclado mecánico con switches blue, retroiluminación RGB y base antideslizante.', 289900.00, 25, 'https://clonesyperifericos.com/wp-content/uploads/2024/08/Teclado-Mecanico-Redragon-K616-RGB-FIZZ-PRO-USB-C-BT-Inalambrico-USB.png', 1, 1, '2026-07-23 15:44:47'),
(2, 'Mouse Gaming Ultra 16K', 'Mouse ergonómico con sensor óptico de 16000 DPI, 8 botones programables y peso ajustable.', 179900.00, 40, 'https://http2.mlstatic.com/D_NQ_NP_951258-MLA45242768107_032021-O.webp', 2, 1, '2026-07-23 15:44:47'),
(3, 'Monitor Curvo 27\" 165Hz', 'Monitor gaming curvo QHD 2560x1440, panel VA, 1ms de respuesta y compatible con FreeSync.', 1149900.00, 12, 'https://media.falabella.com/falabellaCL/146599982_01/w=1500,h=1500,fit=cover', 3, 1, '2026-07-23 15:44:47'),
(4, 'Audífonos Inalámbricos ANC', 'Audífonos over-ear con cancelación activa de ruido, 30h de batería y micrófono integrado.', 349900.00, 18, 'https://m.media-amazon.com/images/I/614XfrOiMRL._AC_UF894,1000_QL80_.jpg', 4, 1, '2026-07-23 15:44:47'),
(5, 'Webcam 4K AutoFocus', 'Cámara web 4K con enfoque automático, corrección de luz y micrófono dual estéreo.', 259900.00, 30, 'https://http2.mlstatic.com/D_Q_NP_2X_865030-MCO82363355023_022025-P.webp', 5, 1, '2026-07-23 15:44:47');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `rol` tinyint(1) NOT NULL,
  `fecha_registro` datetime NOT NULL DEFAULT current_timestamp(),
  `activo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `email`, `telefono`, `password_hash`, `rol`, `fecha_registro`, `activo`) VALUES
(1, 'Administrador', 'admin@clicktech.com', '3001234567', '$2y$10$placeholder_hash_cambiar', 0, '2026-07-23 15:44:47', 1),
(2, 'Carlos Ramírez', 'carlos.ramirez@gmail.com', '3112345678', '$2y$10$placeholder_hash_cambiar', 1, '2026-07-23 15:44:47', 1),
(3, 'María López', 'maria.lopez@hotmail.com', '3209876543', '$2y$10$placeholder_hash_cambiar', 1, '2026-07-23 15:44:47', 1),
(4, 'Andrés García', 'andres.garcia@gmail.com', '3156781234', '$2y$10$placeholder_hash_cambiar', 1, '2026-07-23 15:44:47', 1),
(5, 'Valentina Torres', 'valentina.torres@outlook.com', '3004567890', '$2y$10$placeholder_hash_cambiar', 1, '2026-07-23 15:44:47', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id_categorias`),
  ADD UNIQUE KEY `uk_nombre` (`nombre`);

--
-- Indices de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `fk_detalle_pedido` (`id_pedido`),
  ADD KEY `fk_detalle_producto` (`id_producto`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `fk_pedidos_usuario` (`id_usuario`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `fk_productos_categoria` (`id_categorias`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `uk_email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id_categorias` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD CONSTRAINT `fk_detalle_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_detalle_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `fk_pedidos_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_categoria` FOREIGN KEY (`id_categorias`) REFERENCES `categorias` (`id_categorias`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
