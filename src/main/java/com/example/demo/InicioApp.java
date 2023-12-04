package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.servicio.ProductoServicio;

@Component
public class InicioApp {

    @Autowired
    private ProductoServicio productoServicio;

    @PostConstruct
    public void ejecutarAlInicio() {
        productoServicio.borrarProductoSiExcedioDisponibilidad();
    }
    
}
