package com.example.demo.controlador;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.repositorio.SedeRepositorio;
import com.example.demo.servicio.ProductoServicio;
import com.example.demo.servicio.SedeServicio;

@RestController
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    SedeServicio sedeServicio;


    //PRODUCTO

    @PostMapping("/producto")
    public void agregarProducto(@RequestBody Producto producto){
        productoServicio.agregarProducto(producto);
    }
    
    @PostMapping("/sede/{nombre}")
    public void establecerSede(@PathVariable String nombre, @RequestBody Sede sede){
        productoServicio.establecerSede(nombre, sede);
    }

    @PutMapping("/producto/{nombre}")
    public void actualizarProducto(@PathVariable String nombre, @RequestBody Producto producto){
        productoServicio.actualizarProducto(nombre, producto);
    }

    @PutMapping("/nombre/{nombre}/{nuevoNombre}")
    public void actualizarNombreProducto(@PathVariable String nombre, @PathVariable String nuevoNombre){
        productoServicio.actualizarNombreProducto(nombre, nuevoNombre);
    }

    @PutMapping("/precio/{nombre}/{precio}")
    public void actualizarPrecioProducto(@PathVariable String nombre, @PathVariable Integer precio){
        productoServicio.actualizarPrecioProducto(nombre, precio);
    }

    @PutMapping("/disponibilidad/{nombre}/{disponibilidad}")
    public void actualizarDisponibilidadProducto(@PathVariable String nombre, @PathVariable LocalDate disponibilidad){
        productoServicio.actualizarDisponibilidadProducto(nombre, disponibilidad);
    }

    @PutMapping("/stock{nombre}/{stock}")
    public void actualizarStockProducto(@PathVariable String nombre, @PathVariable Integer stock){
        productoServicio.actualizarStockProducto(nombre, stock);
    }

    @DeleteMapping("/producto/{nombre}")
    public void eliminarProducto(@PathVariable String nombre){
        productoServicio.borrarProducto(nombre);
    }

    //SEDE

    @PostMapping("/sede")
    public void crearSede(@RequestBody Sede sede){
        sedeServicio.agregarSede(sede);
    }

    @PutMapping("/sede/{nombre}")
    public void actualizarSede(@PathVariable String nombre, @RequestBody Sede sede){
        sedeServicio.actualizarSede(nombre, sede);
    }

    @PutMapping("sede/{nombre}/{nombreNuevo}")
    public void actualizarNombreSede(@PathVariable String nombre, @PathVariable String nuevoNombre){
        sedeServicio.actualizarNombre(nombre, nuevoNombre);
    }

    @PutMapping("/sede/{nombre}/{zona}")
    public void actualizarZonaSede(@PathVariable String nombre, @PathVariable String zona){
        sedeServicio.actualizarZona(nombre, zona);
    }

    @PutMapping("/sede/{nombre}/{direccion}")
    public void actualizarDireccionSede(@PathVariable String nombre, @PathVariable String direccion){
        sedeServicio.actualizarDireccion(nombre, direccion);
    }

    @PutMapping("/sede/{nombre}/{horario}")
    public void actualizarHorarioSede(@PathVariable String nombre, @PathVariable String horario){
        sedeServicio.actualizarHorario(nombre, horario);
    }

    @DeleteMapping("/sede/{nombre}")
    public void eliminarSede(@PathVariable String nombre){
        sedeServicio.borrarSede(nombre);
    }





    
}
