package com.example.demo.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Producto;
import com.example.demo.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;
    
    @Transactional /*indicar que e, método debe ser ejecutado dentro de una transacción. 
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
    public void obtenerProductosPorSede(){

    }

    @Transactional
    public void obtenerProductosPorZona(){

        //  Que ponga una zona y le figure la sede mas cercana.
    }

    @Transactional
    public void obtenerProductosPorPrecio(){

    }

    @Transactional
    public void obtenerProductosPorTipo(){
        
    }

    @Transactional
    public void actualizarProductos(){

    }

    @Transactional
    public void borrarProductos(){

    }

    @Transactional
    public void borrarSiExcedioDisponibilidiad(){

    }
}
