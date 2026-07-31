package com.clicktech.backend.controller;

import com.clicktech.backend.dto.ApiResponse;
import com.clicktech.backend.entity.Categoria;
import com.clicktech.backend.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> obtenerTodas() {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        return ResponseEntity.ok(ApiResponse.ok("Categorías obtenidas exitosamente", categorias));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> obtenerPorId(@PathVariable Integer id) {
        try {
            Categoria categoria = categoriaService.obtenerPorId(id);
            return ResponseEntity.ok(ApiResponse.ok("Categoría encontrada", categoria));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Categoria>> crear(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.crear(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Categoría creada exitosamente", nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> actualizar(@PathVariable Integer id, @RequestBody Categoria datos) {
        try {
            Categoria actualizada = categoriaService.actualizar(id, datos);
            return ResponseEntity.ok(ApiResponse.ok("Categoría actualizada exitosamente", actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.ok("Categoría eliminada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
