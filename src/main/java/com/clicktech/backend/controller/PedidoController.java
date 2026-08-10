package com.clicktech.backend.controller;

import com.clicktech.backend.dto.ApiResponse;
import com.clicktech.backend.dto.PedidoRequest;
import com.clicktech.backend.dto.PedidoResponse;
import com.clicktech.backend.dto.ProductoRequest;
import com.clicktech.backend.entity.Pedido;
import com.clicktech.backend.entity.Producto;
import com.clicktech.backend.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {


    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Pedido>>> getAllPedidos(){
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.ok("Pedidos obtenidos exitosamente", pedidos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(@Valid @RequestBody PedidoRequest pedidoRequest){
        try {
            PedidoResponse nuevoPedido = pedidoService.crear(pedidoRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Pedido creado exitosamente", nuevoPedido));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        List<PedidoResponse> pedidos = pedidoService.obtenerPorUsuario(idUsuario);
        return ResponseEntity.ok(ApiResponse.ok("Pedidos del usuario obtenidos exitosamente", pedidos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PedidoRequest request) {
        try {
            PedidoResponse pedidoResponse = pedidoService.actualizar(id, request);
            return ResponseEntity.ok(ApiResponse.ok("Pedido actualizado exitosamente", pedidoResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

}

