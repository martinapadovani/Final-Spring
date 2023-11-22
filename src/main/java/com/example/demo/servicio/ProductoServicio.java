package com.example.demo.servicio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.entidades.Tipo;
import com.example.demo.repositorio.ProductoRepositorio;
import com.example.demo.repositorio.SedeRepositorio;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;

    @Autowired
    SedeRepositorio sedeRepositorio;
    
    @Transactional /*indicar que el método debe ser ejecutado dentro de una transacción. 
    La transacción se utiliza indicar que la operacion deben completarse con éxito o no afectara la DB
    Si el método se ejecuta correctamente, la transacción se compromete; de lo contrario, 
    se realiza un rollback, deshaciendo/revirtiendo todas las operaciones realizadas en la transacción
    Si se realizan varias operaciones en la DB dentro de una única transacción, y alguna de esas falla,
    las demás se revertirán.
    Garantiza la integridad de la DB y manttiene la consistencia de los datos*/
    public void crearProducto(Producto producto){
        productoRepositorio.save(producto);
    }

    @Transactional
    public List<Producto> obtenerProductos(){
        return productoRepositorio.findAll();
    }

    @Transactional
    public List<Producto> obtenerProductosPorSede(Sede sede){
        return productoRepositorio.findBySede(sede);
    }

    @Transactional
    public void obtenerProductosPorZona(){
        //  Que ponga una zona y le figure la sede mas cercana.
    }

    @Transactional
    public void obtenerProductosPorPrecio(){

    }

    @Transactional
    public List<Producto> obtenerProductosPorTipo(Tipo tipo){
        return productoRepositorio.findByTipo(tipo);
    }

    @Transactional
    public void establecerSede(String id, Sede sede){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);

        Producto producto = productoOptional.get();

        producto.getSedes().add(sede);
    }

    @Transactional
    public String actualizarProducto(
    String id, 
    String nombre,
    int precioXKilo,
    int tiempoDisponibilidad,
    int stock){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setNombre(nombre);
            producto.setPrecioXKilo(precioXKilo);
            producto.setStock(stock);
            producto.setTiempoDisponibilidad(tiempoDisponibilidad);
            producto.setStock(stock);

            productoRepositorio.save(producto);
            return "Producto actualizado con éxito";
        }
        return "Error";
    }

    @Transactional
    public String actualizarNombreProducto(String id, String nombre){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setNombre(nombre);

            productoRepositorio.save(producto);
            return "Producto actualizado con éxito";
        }
        return "Error";
    }

    @Transactional
    public String actualizarPrecioProducto(String id, int precio){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setPrecioXKilo(precio);
            
            productoRepositorio.save(producto);
            return "Producto actualizado con éxito";
        }
        return "Error";
    }

    @Transactional
    public String actualizarDisponibilidadProducto(String id, int tiempoDisponibilidad){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setTiempoDisponibilidad(tiempoDisponibilidad);
            
            productoRepositorio.save(producto);
            return "Producto actualizado con éxito";
        }
        return "Error";
    }

    @Transactional
    public String actualizarStockProducto(String id, int stock){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setStock(stock);
            
            productoRepositorio.save(producto);
            return "Producto actualizado con éxito";
        }
        return "Error";
    }

    @Transactional
    public void borrarProductos(String id){

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        Producto producto = productoOptional.get();
        productoRepositorio.delete(producto);

    }

    @Transactional
    public void borrarSiExcedioDisponibilidiad(){

    }
}
