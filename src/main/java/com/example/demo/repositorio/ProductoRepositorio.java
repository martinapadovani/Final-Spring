package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.enumeradores.Tipo;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String> {

    List<Producto> findBySedes(Sede sede);
    List<Producto> findByTipo(Tipo tipo);
    List<Producto> findBySedesZona(String zona);
    
}
