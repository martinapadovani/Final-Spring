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
    public String agregarProducto(@RequestBody Producto producto){
        return productoServicio.agregarProducto(producto);
    }
    
    @PostMapping("/productos/sede/{nombreProducto}/{nombreSede}")
    public String establecerSede(@PathVariable String nombreProducto, @PathVariable String nombreSede){
        return productoServicio.establecerSede(nombreProducto, nombreSede);
    }

    @PutMapping("/producto/{nombre}")
    public String actualizarProducto(@PathVariable String nombre, @RequestBody Producto producto){
        return productoServicio.actualizarProducto(nombre, producto);
    }

    @PutMapping("/nombre/{nombre}/{nuevoNombre}")
    public String actualizarNombreProducto(@PathVariable String nombre, @PathVariable String nuevoNombre){
        return productoServicio.actualizarNombreProducto(nombre, nuevoNombre);
    }

    @PutMapping("/precio/{nombre}/{precio}")
    public String actualizarPrecioProducto(@PathVariable String nombre, @PathVariable Integer precio){
        return productoServicio.actualizarPrecioProducto(nombre, precio);
    }

    @PutMapping("/disponibilidad/{nombre}/{disponibilidad}")
    public String actualizarDisponibilidadProducto(@PathVariable String nombre, @PathVariable LocalDate disponibilidad){
        return productoServicio.actualizarDisponibilidadProducto(nombre, disponibilidad);
    }

    @PutMapping("/stock{nombre}/{stock}")
    public String actualizarStockProducto(@PathVariable String nombre, @PathVariable Integer stock){
        return productoServicio.actualizarStockProducto(nombre, stock);
    }

    @DeleteMapping("/producto/{nombre}")
    public String eliminarProducto(@PathVariable String nombre){
        return productoServicio.borrarProducto(nombre);
    }

    //SEDE

    @PostMapping("/sede")
    public String crearSede(@RequestBody Sede sede){
        return sedeServicio.agregarSede(sede);
    }

    @PutMapping("/sede/{nombre}")
    public String actualizarSede(@PathVariable String nombre, @RequestBody Sede sede){
        return sedeServicio.actualizarSede(nombre, sede);
    }

    @PutMapping("sede/{nombre}/{nombreNuevo}")
    public String actualizarNombreSede(@PathVariable String nombre, @PathVariable String nuevoNombre){
        return sedeServicio.actualizarNombre(nombre, nuevoNombre);
    }

    @PutMapping("/sede/{nombre}/{zona}")
    public String actualizarZonaSede(@PathVariable String nombre, @PathVariable String zona){
        return sedeServicio.actualizarZona(nombre, zona);
    }

    @PutMapping("/sede/{nombre}/{direccion}")
    public String actualizarDireccionSede(@PathVariable String nombre, @PathVariable String direccion){
        return sedeServicio.actualizarDireccion(nombre, direccion);
    }

    @PutMapping("/sede/{nombre}/{horario}")
    public String actualizarHorarioSede(@PathVariable String nombre, @PathVariable String horario){
        return sedeServicio.actualizarHorario(nombre, horario);
    }

    @DeleteMapping("/sede/{nombre}")
    public String eliminarSede(@PathVariable String nombre){
        return sedeServicio.borrarSede(nombre);
    }
}
