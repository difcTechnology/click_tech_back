package com.clicktech.backend.dto;

import com.clicktech.backend.entity.DetallePedido;
import com.clicktech.backend.entity.Pedido;
import com.clicktech.backend.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoRequest {

    private Integer id;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @NotNull(message = "El total es obligatorio")
    private BigDecimal total;
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;
    @NotEmpty(message = "Debe ingresar almenos 1 detalle al pedido")
    private List<DetallePedidoRequest> detallePedidoRequestList = new ArrayList<>();
    @NotNull(message = "El idUsuario es obligatorio")
    private Integer idUsuario;


    public PedidoRequest(String direccion, LocalDateTime fecha, Integer id, String metodoPago, BigDecimal total, Integer idUsuario) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.id = id;
        this.metodoPago = metodoPago;
        this.total = total;
        this.idUsuario = idUsuario;
    }

    public List<DetallePedidoRequest> getDetallePedidoRequestList() {
        return detallePedidoRequestList;
    }

    public void setDetallePedidoRequestList(List<DetallePedidoRequest> detallePedidoRequestList) {
        this.detallePedidoRequestList = detallePedidoRequestList;
    }

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

    public Pedido toEntity() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(this.getIdUsuario());
        Pedido pedido = new Pedido(this.direccion,LocalDateTime.now(),this.metodoPago,this.total,usuario);
        List<DetallePedido> detallePedidoList = new ArrayList<>();

        for(DetallePedidoRequest detallePedidoRequest : detallePedidoRequestList){
            DetallePedido detallePedido = detallePedidoRequest.toEntity();
            detallePedido.setPedido(pedido);
            detallePedidoList.add(detallePedido);
        }
        pedido.setDetalles(detallePedidoList);
        return pedido;
    }
}
