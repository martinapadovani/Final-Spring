package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Sede;
import java.util.List;
import com.example.demo.entidades.Producto;


@Repository
public interface SedeRepositorio extends JpaRepository<Sede, String> {

    List<Sede> findByProductos(Producto producto);
    List<Sede> findByZona(String zona);
    
}
