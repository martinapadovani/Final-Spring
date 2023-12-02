package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.repositorio.SedeRepositorio;
import com.example.demo.servicio.ProductoServicio;

@RestController
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    SedeRepositorio sedeRepositorio;

    @PostMapping("/")
    public void agregarProducto(@RequestBody Producto producto){
        productoServicio.agregarProducto(producto);
    }
    
    @PostMapping("/{id}")
    public void establecerSede(@PathVariable String nombre, @RequestBody Sede sede){
        productoServicio.establecerSede(nombre, sede);
    }





    
}
