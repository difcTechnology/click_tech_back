package com.clicktech.backend.service;

import com.clicktech.backend.entity.Categoria;
import com.clicktech.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findByActiva(1);
    }

    public Categoria obtenerPorId(Integer id) {
        return categoriaRepository.findById(id)
                .filter(c -> c.getActiva() == 1)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + id));
    }

    public Categoria crear(Categoria categoria) {
        categoria.setActiva(1);
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Integer id, Categoria datos) {
        Categoria categoria = obtenerPorId(id);
        categoria.setNombre(datos.getNombre());
        categoria.setDescripcion(datos.getDescripcion());
        categoria.setImagen(datos.getImagen());
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Integer id) {
        Categoria categoria = obtenerPorId(id);
        categoria.setActiva(0);
        categoriaRepository.save(categoria);
    }
}
