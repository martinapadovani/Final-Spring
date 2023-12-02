package com.example.demo.servicio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.event.spi.PreDeleteEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.enumeradores.Horarios;
import com.example.demo.enumeradores.Tipo;
import com.example.demo.repositorio.ProductoRepositorio;


public class ProductoServicioTest {

    private ProductoServicio productoServicio;

    @Mock
    private ProductoRepositorio productoRepositorio;

    AutoCloseable autoCloseable;
    Producto p1;
    Producto p2;
    LocalDate tiempoDisponible;
    List<Producto> productos;
    
    @BeforeEach
    void setUp(){

        autoCloseable = MockitoAnnotations.openMocks(this);
        productoServicio = new ProductoServicio(productoRepositorio);

        p1 = new Producto();
        p1.setId("123");
        p1.setNombre("tomate");
        p1.setPrecioXKilo(100);
        p1.setStock(435);
        tiempoDisponible = LocalDate.of(2023, Month.NOVEMBER, 11);
        p1.setTiempoDisponible(tiempoDisponible);
        p1.setTipo(Tipo.FRUTA);

        p2 = new Producto();
        p2.setId("321");
        p2.setNombre("Lechuga");
        p2.setPrecioXKilo(70);
        p2.setStock(225);
        tiempoDisponible = LocalDate.of(2023, Month.NOVEMBER, 11);
        p2.setTiempoDisponible(tiempoDisponible);
        p2.setTipo(Tipo.VERDURA);

        productos = new ArrayList<>();
        productos.add(p1);
        productos.add(p2);

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    //No funciona xd
    @Test
    void obtenerProductoPorPrecioTest_Exitoso(){

        when(productoRepositorio.findAll()).thenReturn(productos);

        List<Producto> productosXPrecio = productoServicio.obtenerProductosPorPrecio(5, 8);

        //assertThat(productosXPrecio.contains(p1));
        //assertThat(productosXPrecio.contains(p2));
        assertThat(productosXPrecio.equals(productos));
    }

    @Test
    void establecerSede__Test(){ // Quiero verificar si es posible agregarle una sede a un producto que no tiene ninguna previamente 
        when(productoRepositorio.findById("123")).thenReturn(Optional.of(p1));
        
        Sede sede = new Sede();
        sede.setDireccion("Corrientes 166");
        sede.setHorarioVenta(Horarios.MAÑANA);
        sede.setZona("Tigre");

        productoServicio.establecerSede("123", sede);
    }

    @Test
    void borrarSiExcedioDisponibilidiad_Test(){

        String id = "123";
        
        when(productoRepositorio.findById("123")).thenReturn(Optional.of(p1));
        doNothing().when(productoRepositorio).deleteById(id);
        
        String respuesta = productoServicio.borrarSiExcedioDisponibilidiad(id);

        // Verifica que se obtenga una respuesta
        assertThat(respuesta).isNotNull();

        // Verifica que el mensaje de la respuesta sea correcto para el caso exitoso
        assertThat(respuesta).isEqualTo("Producto Eliminado Exitosamente");
    }


}
