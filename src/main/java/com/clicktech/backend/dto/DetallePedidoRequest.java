package com.clicktech.backend.dto;

import com.clicktech.backend.entity.DetallePedido;
import com.clicktech.backend.entity.Producto;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DetallePedidoRequest {

    private Integer id;

    @NotNull(message = "La cantidad en el detalle es obligatoria")
    private Integer cantidad;

    @NotNull(message = "El precio unitario en el detalle es obligatorio")
    private BigDecimal precioUnitario;

    @NotNull(message = "El subtotal en el detalle es obligatorio")
    private BigDecimal subtotal;

    @JsonAlias({"idProducto", "producto_id"})
    @NotNull(message = "El productoId en el detalle es obligatorio")
    private Integer productoId;

    public DetallePedidoRequest() {}

    public DetallePedidoRequest(Integer cantidad, Integer id, BigDecimal precioUnitario, BigDecimal subtotal, Integer productoId) {
        this.cantidad = cantidad;
        this.id = id;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getIdProducto() {
        return productoId;
    }

    public void setIdProducto(Integer idProducto) {
        this.productoId = idProducto;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public DetallePedido toEntity() {
        DetallePedido detallePedido = new DetallePedido(this.cantidad, this.precioUnitario, this.subtotal);
        Producto producto = new Producto();
        producto.setIdProducto(this.productoId);
        detallePedido.setProducto(producto);
        return detallePedido;
    }
}
