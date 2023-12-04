package com.example.demo.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import com.example.demo.enumeradores.Tipo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Producto implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id; 

    @Column(unique = true)
    String nombre;
    Integer precioXKilo;

    @Column
    LocalDate tiempoDisponible;

    Integer stock;
    
    @Enumerated(EnumType.STRING)
    Tipo tipo;


    //@JsonBackReference
    //@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    // significa que las operaciones de persistencia realizadas en la entidad Sede se propagarán a la entidad Producto y viceversa.
    @JoinTable( 
    /* JoinTable define los detalles de la tabla asociativa que conecta las entidades Sede y Producto.
    Esto es ya que ambas tablas tienen que poder referenciar a la otra a traves de una llave foranea.
    Esta tabla intermedia/asociativa (join table) contendrá las llaves foraneas, donde la combinación de las claves foráneas será su clave primaria compuesta.
    La declaracion con al antoacion no es obligatoria (JPA lo genera automaticamente) pero si recomendable 
    */
        name = "Producto_Sede", //Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name= "producto_nombre", referencedColumnName = "nombre"), //Llave foranea que conecta a la tabla de la entidad propietaria de la relacion(sede)
        inverseJoinColumns = @JoinColumn (name="sede_nombre", referencedColumnName = "nombre") //Llave foranea que conecta a la tabla de la entidad inversa de la relacion (Producto)
        //En ambas se establece que la columna refiere a la columna nombre de cada entidad respectivamente.
        //Ya que, por defecto, las claves primarias de las tablas intermedias en las relaciones many-to-many son generalmente combinaciones de las claves primarias de las entidades relacionadas.
        //Sin esa aclaracion tomara el id.
        )
    List<Sede> sedes;
    
}
