package com.example.demo.seguridad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.seguridad.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity 
@RequiredArgsConstructor
public class SecurityConfig {

        //Instanciamos nuestras configuraciones
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final AuthenticationProvider authProvider;

        @Bean //Anotacion para inyectar y aplicar el filtro de seguridad
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                        //Por defecto, Spring invoca CsrfFilter para proteger contra ataques CSRF.
                        //Esto intercepta las sesiones el usuario.
                        //Lo desactivamos ya que vamos a personalizar esta configuracion.
                        .csrf(csrf -> csrf
                                .disable())

                                .authorizeHttpRequests(authRequest -> authRequest
                                .antMatchers("/cliente/**").permitAll()
                                .anyRequest().authenticated())                            
                        // desactivamos la autenticación propia de Sping Security mediante sesiones, para personalizar su config
                                .sessionManagement(sessionManager -> sessionManager
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                        // le indicamos la política de autenticación que deseamos:
                                .authenticationProvider(authProvider)
                        // y la clase que usaremos para el filtro con JWT, para que lo construya
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
    
}
