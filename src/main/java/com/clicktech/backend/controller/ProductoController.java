package com.clicktech.backend.controller;

import com.clicktech.backend.dto.ApiResponse;
import com.clicktech.backend.dto.ProductoRequest;
import com.clicktech.backend.entity.Producto;
import com.clicktech.backend.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.ok("Productos obtenidos exitosamente", productos));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtenerPorId(@PathVariable Integer id) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            return ResponseEntity.ok(ApiResponse.ok("Producto encontrado", producto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerPorCategoria(@PathVariable Integer idCategoria) {
        List<Producto> productos = productoService.obtenerPorCategoria(idCategoria);
        return ResponseEntity.ok(ApiResponse.ok("Productos de la categoría " + idCategoria, productos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crear(@Valid @RequestBody ProductoRequest request) {
        try {
            Producto nuevoProducto = productoService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Producto creado exitosamente", nuevoProducto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoRequest request) {
        try {
            Producto actualizado = productoService.actualizar(id, request);
            return ResponseEntity.ok(ApiResponse.ok("Producto actualizado exitosamente", actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.ok("Producto eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
