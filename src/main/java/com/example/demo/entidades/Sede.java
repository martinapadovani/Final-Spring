package com.example.demo.entidades;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.print.DocFlavor.STRING;

import org.hibernate.annotations.GenericGenerator;
    
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
public class Sede {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

    String zona;
    String direccion;

    @Enumerated(EnumType.STRING)
    Horarios horarioVenta;
    
    @ManyToMany(cascade = CascadeType.ALL) // significa que las operaciones de persistencia realizadas en la entidad Sede se propagarán a la entidad Producto y viceversa.
    @JoinTable( 
    /* JoinTable define los detalles de la tabla asociativa que conecta las entidades Sede y Producto.
    Esto es ya que ambas tablas tienen que poder referenciar a la otra a traves de una llave foranea.
    Esta tabla intermedia/asociativa (join table) contendrá las llaves foraneas, donde la combinación de las claves foráneas será su clave primaria compuesta.
    La declaracion con al antoacion no es obligatoria (JPA lo genera automaticamente) pero si recomendable 
    */
    name = "Producto_Sede", //Nombre de la tabla intermedia
    joinColumns = @JoinColumn(name= "sede_id"), //Llave foranea que conecta a la tabla de la entidad propietaria de la relacion(sede)
    inverseJoinColumns = @JoinColumn (name="producto_id") //Llave foranea que conecta a la tabla de la entidad inversa de la relacion (Producto)
    )
    List<Producto> productos;
    
}