package com.example.demo.repositorio;

import java.util.List;

import javax.swing.event.InternalFrameEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String> {

    List<Producto> findBySede(Sede sede);
    
}
