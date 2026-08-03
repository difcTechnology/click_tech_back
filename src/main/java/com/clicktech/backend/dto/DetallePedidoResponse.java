package com.clicktech.backend.dto;

import com.clicktech.backend.entity.DetallePedido;
import com.clicktech.backend.entity.Pedido;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DetallePedidoResponse {

    private Integer id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Integer productoId;

    public DetallePedidoResponse(Integer cantidad, Integer id, BigDecimal precioUnitario, Integer productoId, BigDecimal subtotal) {
        this.cantidad = cantidad;
        this.id = id;
        this.precioUnitario = precioUnitario;
        this.productoId = productoId;
        this.subtotal = subtotal;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public static DetallePedidoResponse fromEntity(DetallePedido detallePedido){
        DetallePedidoResponse detallePedidoResponse = new DetallePedidoResponse(
                detallePedido.getCantidad(),
                detallePedido.getId(),
                detallePedido.getPrecioUnitario(),
                detallePedido.getProducto().getIdProducto(),
                detallePedido.getSubtotal());
        return detallePedidoResponse;
    }
}
