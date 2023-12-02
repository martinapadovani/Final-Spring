package com.example.demo.servicio;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.enumeradores.Tipo;
import com.example.demo.exception.exceptions.BadRequestException;
import com.example.demo.exception.exceptions.NotFoundException;
import com.example.demo.repositorio.ProductoRepositorio;
import com.example.demo.repositorio.SedeRepositorio;

@Service
public class ProductoServicio {


    @Autowired
    ProductoRepositorio productoRepositorio;

    @Autowired
    SedeRepositorio sedeRepositorio;
    
    public ProductoServicio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

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

        if(productoRepositorio.findAll().isEmpty()){
            throw new NotFoundException("No se encontraron productos en la DB.");
        }
        return productoRepositorio.findAll();
    }

    @Transactional
    public Producto obtenerPorId (String id) {

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }

        
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        return productoRepositorio.findById(id).get();
    }

    @Transactional
    public List<Producto> obtenerProductosPorSede(Sede sede){

        if (!(sede instanceof Sede) || sede == null) {
            throw new BadRequestException("El tipo de sede no es válido. Sede: " + sede);
        }
        
        if(productoRepositorio.findBySedes(sede).isEmpty()) {
            throw new NotFoundException("No existe ningun producto asociado a esa sede. Sede: " + sede);
        }
        return productoRepositorio.findBySedes(sede);
    }  

    @Transactional
    public List<Producto> obtenerProductosPorZona(String zona){

        if (!(zona instanceof String) || zona == null) {
            throw new BadRequestException("El tipo de zona no es válido. Zona: " + zona);
        }
        
        if(productoRepositorio.findBySedesZona(zona).isEmpty()) {
            throw new NotFoundException("No existe ningun producto asociado a esa zona. Zona: " + zona);
        }
        return productoRepositorio.findBySedesZona(zona);
    }

    // @Transactional
    // public List<Producto> obtenerProductosPorPrecio(int rango1, int rango2) {

    //     List<Producto> productos = productoRepositorio.findAll();
    //     List<Producto> productosRango = new ArrayList<>();

    //     for (Producto producto : productos) {
    //         if (producto.getPrecioXKilo() >= rango1 && producto.getPrecioXKilo() <= rango2) {
    //             productosRango.add(producto);
    //             return productosRango;
    //         }
    //     }
    //     return productosRango;
    // }


    @Transactional
    public List<Producto> obtenerProductosPorPrecio(Integer precioMenor, Integer precioMayor){
        
        List<Producto> productos = new ArrayList<>();
        productos = (List<Producto>) productoRepositorio.findAll();
        
        List<Producto> productosRangoMayor = new ArrayList<>();
        List<Producto> productosRangoMenor = new ArrayList<>();

        if (!(precioMayor instanceof Integer) || precioMayor == null) {
            throw new BadRequestException("El tipo de precio no es válido. Precios: " + precioMenor);
        }

        if (!(precioMenor instanceof Integer) || precioMenor == null) {
            throw new BadRequestException("El tipo de precio no es válido. Precio: " + precioMayor);
        }
        
        if(productos.isEmpty()) {
            throw new NotFoundException("No se encontraron productos para filtrar");
        }

        for (Producto producto : productos) {
            boolean esMenor = producto.getPrecioXKilo() <= precioMayor;
            boolean esMayor = producto.getPrecioXKilo() >= precioMenor;

            if (esMenor) {
                if(!esMenor) {
                    throw new NotFoundException("No se encontraron productos con un menor precio al indicado. Precio: " + precioMenor);
                }
                productosRangoMenor.add(producto);

            }if(esMayor){

                if(!esMayor){
                    throw new NotFoundException("No se encontraron productos con un mayor precio al indicado. Precio: " + precioMayor);
                }
                productosRangoMayor.add(producto);
            }
        }
        List<Producto> productosRango = new ArrayList<Producto>(){{
            addAll(productosRangoMayor);
            addAll(productosRangoMenor);
        }};

        return productosRango;
    }


    @Transactional
    public List<Producto> obtenerProductosPorTipo(Tipo tipo){

        if (!(tipo instanceof Tipo) || tipo  == null) {
            throw new BadRequestException("El tipo no es válido. Tipo: " + tipo);
        }
        
        if(productoRepositorio.findByTipo(tipo).isEmpty()) {
            throw new NotFoundException("No existe ningun producto del tipo indicado. Tipo: " + tipo);
        }

        return productoRepositorio.findByTipo(tipo);
    }

    @Transactional
    public void establecerSede(String id, Sede sede){

        if (!(id instanceof String) || id  == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        
        if (!(sede instanceof Sede) || sede  == null) {
            throw new BadRequestException("El tipo de sede no es válido. Sede: " + sede);
        }

        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);

        Producto producto = productoOptional.get();

        if(producto.getSedes() == null){ //Si no tenia ninguna sede indicada
        
            List<Sede> sedes = new ArrayList<>(); //Debo crear la lista
            sedes.add(sede); // agrego la sede a la lista
            producto.setSedes(sedes); //seteo la lista

        }else{//Si ya tenia indicada al menos una Sede:
            producto.getSedes().add(sede);
        }
        
    }

    @Transactional
    public String actualizarProducto(
    String id, 
    String nombre,
    Integer precioXKilo,
    LocalDate tiempoDisponible,
    Integer stock){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El tipo del valor nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if (!(precioXKilo instanceof Integer) || precioXKilo == null) {
            throw new BadRequestException("El tipo de precio no es válido. Precio: " + precioXKilo);
        }
        if (!(tiempoDisponible instanceof LocalDate) || tiempoDisponible  == null) {
            throw new BadRequestException("El tipo del valor tiempoDisponible no es válido. Tiempo Disponible: " + tiempoDisponible);
        }
        if (!(stock instanceof Integer) || stock  == null) {
            throw new BadRequestException("El tipo de stock no es válido. Precio: " + stock);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);

        Producto producto = productoOptional.get();
        producto.setNombre(nombre);
        producto.setPrecioXKilo(precioXKilo);
        producto.setStock(stock);
        producto.setTiempoDisponible(tiempoDisponible);
        producto.setStock(stock);

        productoRepositorio.save(producto);
        return "Producto actualizado con éxito";
    }

    @Transactional
    public String actualizarNombreProducto(String id, String nombre){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El tipo del valor nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);

        Producto producto = productoOptional.get();
        producto.setNombre(nombre);

        productoRepositorio.save(producto);
        return "Nombre actualizado con éxito";
    }

    @Transactional
    public String actualizarPrecioProducto(String id, Integer precio){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }
        if (!(precio instanceof Integer) || precio == null) {
            throw new BadRequestException("El tipo de precio no es válido. Precio: " + precio);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        Producto producto = productoOptional.get();
        producto.setPrecioXKilo(precio);
        
        productoRepositorio.save(producto);
        return "Precio actualizado con éxito";
    }

    @Transactional
    public String actualizarDisponibilidadProducto(String id, LocalDate tiempoDisponible){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(tiempoDisponible instanceof LocalDate) || tiempoDisponible  == null) {
            throw new BadRequestException("El tipo del valor tiempoDisponible no es válido. Tiempo Disponible: " + tiempoDisponible);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);

        Producto producto = productoOptional.get();
        producto.setTiempoDisponible(tiempoDisponible);
        
        productoRepositorio.save(producto);
        return "Tiempo disponible actualizado con éxito";

    }

    @Transactional
    public String actualizarStockProducto(String id, Integer stock){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(stock instanceof Integer) || stock  == null) {
            throw new BadRequestException("El tipo de stock no es válido. Precio: " + stock);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);

        Producto producto = productoOptional.get();
        producto.setStock(stock);
        
        productoRepositorio.save(producto);
        return "Stock actualizado con éxito";

    }

    @Transactional
    public void borrarProducto(String id){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        Producto producto = productoOptional.get();
        productoRepositorio.delete(producto);

    }

    @Transactional
    public String borrarSiExcedioDisponibilidiad(String id){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        Optional<Producto> productoOptional = productoRepositorio.findById(id);
        Producto producto = productoOptional.get();

        LocalDate tiempoDisponible = producto.getTiempoDisponible();
        LocalDate fechaActual = LocalDate.now();

        Period tiempoRestante = fechaActual.until(tiempoDisponible);
        int dias = tiempoRestante.getDays();

        System.out.println(dias);

        if (dias <= 0) {
            productoRepositorio.delete(producto);

            return "El tiempo disponible ha finalizado. Producto Eliminado Exitosamente";
        }

        return "Se encuentra dentro del plazo de disponibilidad";
    }
}
