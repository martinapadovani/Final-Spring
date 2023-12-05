package com.example.demo.repositorio;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.entidades.Producto;
import com.example.demo.entidades.Sede;
import com.example.demo.enumeradores.Horarios;
import com.example.demo.enumeradores.Tipo;

@DataJpaTest
public class ProductoRepositorioTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    Producto producto;
    Sede sede;
    Sede sede1;
    LocalDate tiempoDisponible;

    @BeforeEach
    public void setUp(){

        sede = new Sede();
        sede.setDireccion("Corrientes 166");
        sede.setHorarioVenta(Horarios.MAÑANA);
        sede.setZona("Tigre");

        sede1 = new Sede();
        sede.setDireccion("Rivadavia 2134");
        sede.setHorarioVenta(Horarios.NOCHE);
        sede.setZona("Mardel");

        //Inicio un producto en la DB, en base al cual realizare los Test
        producto = new Producto();
        producto.setNombre("tomate");
        producto.setPrecioXKilo(100);
        producto.setStock(435);
        tiempoDisponible = LocalDate.of(2023, Month.APRIL, 11);
        producto.setTiempoDisponible(tiempoDisponible);
        producto.setTipo(Tipo.FRUTA);

        List<Producto> productos = new ArrayList<>();
        productos.add(producto);
        sede.setProductos(productos);

        List<Sede> sedes = new ArrayList<>();
        sedes.add(sede);
        producto.setSedes(sedes);

        entityManager.persist(producto);
        entityManager.persist(sede);
        entityManager.persist(sede1);
        //Persisto las entidades principales
        /*No es necesario persistir explícitamente las listas o las entidades relacionadas, ya que
        Hibernate automáticamente manejará las asociaciones y persistirá las entidades relacionadas*/
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindBySedes(){

        List<Producto> productos = productoRepositorio.findBySedes(sede);
        
        assertThat(productos).isNotNull(); 
        assertThat(productos.contains(producto));  
    }

    @Test
    public void testFindBySedesFail(){

        List<Producto> productos = productoRepositorio.findBySedes(sede1);
        
        assertThat(productos).isEmpty();
    }

    @Test 
    public void testFindBySedesZona(){

        List<Producto> productos = productoRepositorio.findBySedesZona("Tigre");

        assertThat(productos).isNotNull(); 
        assertThat(productos.contains(producto));  
    }

    @Test 
    public void testFindBySedesZonaFail(){

        List<Producto> productos = productoRepositorio.findBySedesZona("Mardel");

        assertThat(productos).isEmpty();
    }
        
    
}
