package com.example.demo.servicio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entidades.Producto;
import com.example.demo.enumeradores.Tipo;
import com.example.demo.repositorio.ProductoRepositorio;

public class ProductoServicioTest {

    private ProductoServicio productoServicio;

    @Mock
    private ProductoRepositorio productoRepositorio;

    AutoCloseable autoCloseable;
    Producto producto;
    LocalDate tiempoDisponible;
    
    @BeforeEach
    void setUp(){

        autoCloseable = MockitoAnnotations.openMocks(this);

        productoServicio = new ProductoServicio(productoRepositorio);

        producto = new Producto();
        producto.setId("123");
        producto.setNombre("tomate");
        producto.setPrecioXKilo(100);
        producto.setStock(435);
        tiempoDisponible = LocalDate.of(2023, Month.NOVEMBER, 11);
        producto.setTiempoDisponible(tiempoDisponible);
        producto.setTipo(Tipo.FRUTA);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void borrarSiExcedioDisponibilidiad_Test(){

        String id = "123";
        
        when(productoRepositorio.findById("123")).thenReturn(Optional.of(producto));
        doNothing().when(productoRepositorio).deleteById(id);
        
        String respuesta = productoServicio.borrarSiExcedioDisponibilidiad(id);

        // Verifica que se obtenga una respuesta
        assertThat(respuesta).isNotNull();

        // Verifica que el mensaje de la respuesta sea correcto para el caso exitoso
        assertThat(respuesta).isEqualTo("Producto Eliminado Exitosamente");
    }


}
