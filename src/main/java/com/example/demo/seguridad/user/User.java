package com.example.demo.seguridad.user;


import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User implements UserDetails{

    //Implemento UserDetails, para indicar que va a ser un tipo de usuario que se logea
    @Id
    @GeneratedValue
    Integer id;
    @Basic
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String lastname;
    String firstname;
    String country;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    //GrantedAuthority es una interfaz que me permite gestionar 
    //las authorizaciones del usuario según su rol
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Retorna una lista con los permisos del usuario, en base a su rol
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    //Los dejamos en true, ya que no los vamos a configurar desde el UserDetails
    //Si no que las personalizaremos, en el JWT
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
