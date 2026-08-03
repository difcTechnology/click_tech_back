package com.clicktech.backend.dto;


import com.clicktech.backend.entity.DetallePedido;
import com.clicktech.backend.entity.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PedidoResponse {

    private Integer id;
    private String direccion;
    private BigDecimal total;
    private String metodoPago;
    private LocalDateTime fecha;
    private Integer idUsuario;
    private List<DetallePedidoResponse> detallePedidoResponseList = new ArrayList<>();


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetallePedidoResponse> getDetallePedidoResponseList() {
        return detallePedidoResponseList;
    }

    public void setDetallePedidoResponseList(List<DetallePedidoResponse> detallePedidoResponseList) {
        this.detallePedidoResponseList = detallePedidoResponseList;
    }

    public static PedidoResponse fromEntity(Pedido pedido){
        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(pedido.getId());
        pedidoResponse.setDireccion(pedido.getDireccion());
        pedidoResponse.setTotal(pedido.getTotal());
        pedidoResponse.setMetodoPago(pedido.getMetodoPago());
        pedidoResponse.setFecha(pedido.getFecha());
        pedidoResponse.setIdUsuario(pedido.getUsuario().getIdUsuario());
        pedidoResponse.setDetallePedidoResponseList(new ArrayList<>());
        for(DetallePedido detallePedido: pedido.getDetalles()){
            pedidoResponse.getDetallePedidoResponseList().add(DetallePedidoResponse.fromEntity(detallePedido));
        }
        return pedidoResponse;
    }
}
