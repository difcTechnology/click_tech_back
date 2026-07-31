package com.clicktech.backend.repository;

import com.clicktech.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByActivo(Integer activo);
    List<Producto> findByCategoriaIdCategoriasAndActivo(Integer idCategoria, Integer activo);
}
