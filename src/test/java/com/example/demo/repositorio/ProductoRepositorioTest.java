package com.example.demo.repositorio;

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

    @BeforeEach
    public void setUp(){

        sede = new Sede();
        sede.setDireccion("Corrientes 166");
        sede.setHorarioVenta(Horarios.MAÑANA);
        sede.setZona("Tigre");

        List<Producto> productos = new ArrayList<>();
        sede.setProductos(productos);

        //Inicio un producto en la DB, en base al cual realizare los Test
        producto = new Producto();
        producto.setNombre("tomate");
        producto.setPrecioXKilo(100);
        producto.setStock(435);
        producto.setTiempoDisponibilidad(23);
        producto.setTipo(Tipo.FRUTA);
        List<Sede> sedes = new ArrayList<>();
        producto.setSedes(sedes);

        productos.add(producto);
        sedes.add(sede);

        entityManager.persist(producto);
        entityManager.persist(sede);
        //Persisto las entidades principales
        /*No es necesario persistir explícitamente las listas o las entidades relacionadas, ya que
        Hibernate automáticamente manejará las asociaciones y persistirá las entidades relacionadas*/
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindBySede(){

        List<Producto> productos1 = productoRepositorio.findBySedes(sede);
        
        assertThat(productos1).isNotNull(); 
        assertThat(productos1.contains(producto));  
    }
    
}
