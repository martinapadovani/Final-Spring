package com.example.demo.entidades;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import com.example.demo.enumeradores.Horarios;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Sede implements Serializable{ 
    // indicar que una clase es "serializable", es decir, que sus instancias pueden ser convertidas en una secuencia de bytes y luego reconstruidas.
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

    @Column(unique = true)
    String nombre;

    String zona;
    String direccion;

    @Enumerated(EnumType.STRING)
    Horarios horarioVenta;
    
    //@JsonManagedReference
    @JsonIgnore
    @ManyToMany(mappedBy = "sedes") //especifica el campo de la entidad propietaria (sede) que mapea la relacion
    List<Producto> productos;
    
}