package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.servicio.ProductoServicio;
import com.example.demo.servicio.SedeServicio;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    SedeServicio sedeServicio;

    //PRODUCTOS

    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        return productoServicio.obtenerProductos();
    }

    @GetMapping("/producto/id/{id}")
    public Producto obtenerProductoPorId(@PathVariable String id){

        return productoServicio.obtenerPorId(id);
    }

    @GetMapping("/productos/sede/{sede}")
    public List<Producto> obtenerProductosPorSede(@PathVariable String sede){

        return productoServicio.obtenerProductosPorSede(sede);
    }

    @GetMapping("/productos/zona/{zona}")
    public List<Producto> obtenerProductosPorZona(@PathVariable String zona){

        return productoServicio.obtenerProductosPorZona(zona);
    }

    @GetMapping("/productos/precio/{p1}/{p2}")
    public List<Producto> obtenerProductosPorRangoPrecio(@PathVariable Integer p1, @PathVariable Integer p2){

        return productoServicio.obtenerProductosPorPrecio(p1, p2);
    }

    @GetMapping("/productos/tipo/{tipo}")
    public List<Producto> obtenerProductosPorTipo(@PathVariable String tipo){

        return productoServicio.obtenerProductosPorTipo(tipo);
    }

    //SEDES

    @GetMapping("/sedes")
    public List<Sede> obtenerSedes(){
        return sedeServicio.obtenerSedes();
    }

    @GetMapping("/sede/id/{id}")
    public Sede obtenerSedePorId(@PathVariable String id){
        return sedeServicio.obtenerSedePorId(id);
    }

    @GetMapping("/sedes/zona/{zona}")
    public List<Sede> obtenerSedesPorZona(@PathVariable String zona){
        return sedeServicio.obtenerSedesPorZona(zona);
    }

    
}
