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
    public String agregarProducto(Producto producto){
        System.out.println("Ingreso al metodo");

        if(productoRepositorio.findByNombre(producto.getNombre()).isPresent()){
            throw new BadRequestException("Ya existe un producto con ese nombre! Nombre: " + producto.getNombre());
        }
        if(!(producto.getNombre() instanceof String || producto.getNombre() == null)){
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + producto.getNombre());
        }
        if (!(producto.getPrecioXKilo() instanceof Integer) || producto.getPrecioXKilo() == null) {
            throw new BadRequestException("El tipo de precio no es válido. Precio: " + producto.getPrecioXKilo());
        }
        if (!(producto.getStock() instanceof Integer) || producto.getStock()  == null) {
            throw new BadRequestException("El tipo de stock no es válido. Precio: " + producto.getStock());
        } 
        if (!(producto.getTiempoDisponible() instanceof LocalDate) || producto.getTiempoDisponible()  == null) {
            throw new BadRequestException("El tipo del valor tiempoDisponible no es válido. Tiempo Disponible: " + producto.getTiempoDisponible());
        }        
        if (producto.getTipo()  == null) {
            throw new BadRequestException("El tipo no es válido. Tipo: " + producto.getTipo());
        }
        // if(!(producto.getTipo() instanceof Tipo)){ //Seguro ingreso un string 
        //     // System.out.println("Tomó la excepcion");
        //     // Tipo tipoIngresado =  producto.getTipo();
        //     // String tipoMayusculas = tipoIngresado.toString().toUpperCase();
        //     // Tipo tipoEnum = Tipo.valueOf(tipoMayusculas);
        //     // producto.setTipo(tipoEnum);  
        // }

        try{
            productoRepositorio.save(producto);
            return "Producto creado con éxito!";
        }catch(Exception e){
            return e.getMessage();
        }
        
    }

    @Transactional
    public List<Producto> obtenerProductos(){

        if(productoRepositorio.findAll().isEmpty()){
            throw new NotFoundException("No se encontraron productos en la DB.");
        }
        return productoRepositorio.findAll();
    }

    @Transactional
    public Producto obtenerPorId(String id) {

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(productoRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("El producto solicitado no existe. ID: " + id);
        }

        return productoRepositorio.findById(id).get();
    }

    @Transactional
    public List<Producto> obtenerProductosPorSede(String nombreSede){

        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombreSede);

        Sede sede = sedeOptional.get();

        if (!(nombreSede instanceof String) || nombreSede == null) {
            throw new BadRequestException("El tipo de sede no es válido. Sede: " + nombreSede);
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
    public List<Producto> obtenerProductosPorTipo(String tipo){

        String tipoMayusculas = tipo.toUpperCase();
        Tipo tipoEnum = Tipo.valueOf(tipoMayusculas);

        if (!(tipo instanceof String) || tipo  == null) {
            throw new BadRequestException("El tipo no es válido. Tipo: " + tipo);
        }
        
        if(productoRepositorio.findByTipo(tipoEnum).isEmpty()) {
            throw new NotFoundException("No existe ningun producto del tipo indicado. Tipo: " + tipo);
        }

        return productoRepositorio.findByTipo(tipoEnum);
    }

    @Transactional
    public String establecerSede(String nombreProducto, String nombreSede){
            
        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombreProducto);
        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombreSede);

        if (!(nombreProducto instanceof String) || nombreProducto  == null) {
            throw new BadRequestException("El valor del nombre no es válido. Se esperaba un String. Nombre: " + nombreProducto);
        }
        if (!(nombreSede instanceof String) || nombreSede  == null) {
            throw new BadRequestException("El tipo de sede no es válido. Sede: " + nombreSede);
        }
        if (!(sedeOptional.isPresent())) {
            throw new BadRequestException("No se encontro una sede bajo ese nombre. Sede: " + nombreSede);
        }
        if(!(sedeOptional.isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombreProducto);
        }

        Producto producto = productoOptional.get();
        Sede sede = sedeOptional.get();

        if(producto.getSedes() == null){ //Si no tenia ninguna sede indicada
        
            List<Sede> sedes = new ArrayList<>(); //Debo crear la lista
            sedes.add(sede); // agrego la sede a la lista
            producto.setSedes(sedes); //seteo la lista

        }else{//Si ya tenia indicada al menos una Sede:
            producto.getSedes().add(sede);
        }

        return "Sede establecida con éxito!";
        
    }

    @Transactional
    public String actualizarProducto(String nombre, Producto producto){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if(!(productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);
        Producto productoDB = productoOptional.get();
        Producto productoActualizado = producto;
        
        productoDB.setNombre(productoActualizado.getNombre());
        productoDB.setPrecioXKilo(productoActualizado.getPrecioXKilo());
        productoDB.setStock(productoActualizado.getStock());
        productoDB.setTiempoDisponible(productoActualizado.getTiempoDisponible());
        productoDB.setTipo(productoActualizado.getTipo());

        productoRepositorio.save(productoDB);
        return "Producto actualizado con éxito";
    }

    @Transactional
    public String actualizarNombreProducto(String nombre, String nuevoNombre){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if (!(nuevoNombre instanceof String) || nuevoNombre == null) {
            throw new BadRequestException("El tipo del valor nombre no es válido. Se esperaba un String. Nombre: " + nuevoNombre);
        }
        if(!(productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);

        Producto producto = productoOptional.get();
        producto.setNombre(nuevoNombre);

        productoRepositorio.save(producto);
        return "Nombre actualizado con éxito";
    }

    @Transactional
    public String actualizarPrecioProducto(String nombre, Integer precio){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El tipo del valor nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if(!(productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }
        if (!(precio instanceof Integer) || precio == null) {
            throw new BadRequestException("El tipo de precio no es válido. Precio: " + precio);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);
        Producto producto = productoOptional.get();
        producto.setPrecioXKilo(precio);
        
        productoRepositorio.save(producto);
        return "Precio actualizado con éxito";
    }

    @Transactional
    public String actualizarDisponibilidadProducto(String nombre, LocalDate tiempoDisponible){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El tipo del valor nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if((!productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }
        if (!(tiempoDisponible instanceof LocalDate) || tiempoDisponible  == null) {
            throw new BadRequestException("El tipo del valor tiempoDisponible no es válido. Tiempo Disponible: " + tiempoDisponible);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);

        Producto producto = productoOptional.get();
        producto.setTiempoDisponible(tiempoDisponible);
        
        productoRepositorio.save(producto);
        return "Tiempo disponible actualizado con éxito";

    }

    @Transactional
    public String actualizarStockProducto(String nombre, Integer stock){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if(!(productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }
        if (!(stock instanceof Integer) || stock  == null) {
            throw new BadRequestException("El tipo de stock no es válido. Precio: " + stock);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);

        Producto producto = productoOptional.get();
        producto.setStock(stock);
        
        productoRepositorio.save(producto);
        return "Stock actualizado con éxito";

    }

    @Transactional
    public String borrarProducto(String nombre){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if(!(productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);
        Producto producto = productoOptional.get();
        productoRepositorio.delete(producto);

        return "El producto " + producto.getNombre() + ", ha sido eliminado con éxito";

    }

    @Transactional
    public String borrarSiExcedioDisponibilidiad(String nombre){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Se esperaba un String. Nombre: " + nombre);
        }
        if(!(productoRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("El producto solicitado no existe. Nombre: " + nombre);
        }

        Optional<Producto> productoOptional = productoRepositorio.findByNombre(nombre);
        Producto producto = productoOptional.get();

        LocalDate tiempoDisponible = producto.getTiempoDisponible();
        LocalDate fechaActual = LocalDate.now();

        Period tiempoRestante = fechaActual.until(tiempoDisponible);
        int dias = tiempoRestante.getDays();

        System.out.println(dias);

        if (dias <= 0) {
            productoRepositorio.delete(producto);

            return "El tiempo disponible ha finalizado. El producto " + producto.getNombre() + ", ha sido eliminado";
        }

        return "Se encuentra dentro del plazo de disponibilidad";
    }
}
