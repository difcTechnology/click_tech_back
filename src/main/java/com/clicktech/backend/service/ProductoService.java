package com.clicktech.backend.service;

import com.clicktech.backend.dto.ProductoRequest;
import com.clicktech.backend.entity.Categoria;
import com.clicktech.backend.entity.Producto;
import com.clicktech.backend.repository.CategoriaRepository;
import com.clicktech.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }
    public List<Producto> obtenerTodos() {
        return productoRepository.findByActivo(1);
    }

    public Producto obtenerPorId(Integer id) {
        return productoRepository.findById(id)
                .filter(p -> p.getActivo() == 1)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));
    }
    public List<Producto> obtenerPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaIdCategoriasAndActivo(idCategoria, 1);
    }
    public Producto crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + request.getIdCategoria()));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setImagen(request.getImagen());
        producto.setCategoria(categoria);
        producto.setActivo(1);

        return productoRepository.save(producto);
    }
    public Producto actualizar(Integer id, ProductoRequest request) {
        Producto producto = obtenerPorId(id);

        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + request.getIdCategoria()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setImagen(request.getImagen());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }
    public void eliminar(Integer id) {
        Producto producto = obtenerPorId(id);
        producto.setActivo(0);
        productoRepository.save(producto);
    }

    public boolean hayStockProducto(Integer idProducto, Integer cantidad){
        Producto producto = obtenerPorId(idProducto);
        return producto.getStock() >= cantidad;
    }
}
