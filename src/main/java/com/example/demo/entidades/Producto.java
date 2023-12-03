package com.example.demo.entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import com.example.demo.enumeradores.Tipo;

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
public class Producto {

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

    @ManyToMany(mappedBy = "productos") //especifica el campo de la entidad propietaria (sede) que mapea la relacion
    List<Sede> sedes;
    
}
