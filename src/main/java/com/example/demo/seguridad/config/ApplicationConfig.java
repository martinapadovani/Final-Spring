package com.example.demo.seguridad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.seguridad.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

        private final UserRepository userRepository;

    @Bean
    //Para configurar el AuthenticationManager, usamos la implementación AutenticationConfiguration, enviada por parámetro (pertence a Spring)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    //Conecto al AuthenticationManager con Provedor de accesos, que en este caso, es del tipo Data Acces Object. (MySQL)
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //Seteo el userDetailsServic al Provider (de tipo DAO)
        authenticationProvider.setUserDetailsService(userDetailService());

        //Indicamos el método con el que estamos encriptando las contraseñas
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean //Declaro una funcion para encriptar las contraseñas
    public PasswordEncoder passwordEncoder() {
        //La funcion retorna/implementa el metodo de encriptacion de BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean //Metodo que retorna el User Detail en base al username 
    public UserDetailsService userDetailService() { // Retorna el usuario en la DB que concuerda con el mail recibido ??
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not fournd"));
    }
    
}
