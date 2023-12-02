package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.repositorio.SedeRepositorio;
import com.example.demo.servicio.ProductoServicio;

@RestController
@RequestMapping("/")
public class ClienteControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    SedeRepositorio sedeRepositorio;

    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        return productoServicio.obtenerProductos();
    }

    @GetMapping("/producto/{id}")
    public Producto obtenerProductoPorId(@PathVariable String id){

        return productoServicio.obtenerPorId(id);
    }

    @GetMapping("/productos")
    public List<Producto> obtenerProductosPorSede(@RequestBody String sede){

        return productoServicio.obtenerProductosPorSede(sede);
    }

    @GetMapping("/productos/{zona}")
    public List<Producto> obtenerProductosPorZona(@PathVariable String zona){

        return productoServicio.obtenerProductosPorZona(zona);
    }

    @GetMapping("/productos/{p1}/{p2}")
    public List<Producto> obtenerProductosPorRangoPrecio(@PathVariable Integer p1, @PathVariable Integer p2){

        return productoServicio.obtenerProductosPorPrecio(p1, p1);
    }

    @GetMapping("/productos/{tipo}")
    public List<Producto> obtenerProductosPorTipo(@PathVariable String tipo){

        return productoServicio.obtenerProductosPorTipo(tipo);
    }



    
}
