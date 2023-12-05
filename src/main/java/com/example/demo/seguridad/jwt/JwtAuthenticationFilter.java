package com.example.demo.seguridad.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{

        //Variables con el atributo final para que genere el contructor
        final String token = getTokenFromRequest(request); //toma el token de la peticion
        final String username;

        //Si es nulo, delegamos el control de la peticion a la cadena de filtros, que controla ese tipo de casos
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //Obtenemos el username del usuario desde el token, y lo asignamos a nuestra variable
        username = jwtService.getUsernameFromToken(token);


        //OBTENER EL ROL DEL USUARIO:
        //Validamos username (obtenido desde el token)
        // si es distinto de nulo y su autenticación no la encuentra en el contexto actual de la sesión (el token)
        //Es decir, si necesita informacion adicional que no está dentro del token (el rol)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //entonces, busca el usuario en la DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            /*Lo asigno a un objeto UserDetails porqué estamos trabajando sobre el usuario que está intentando ingresar,
             no es aún un usuario de tipo USER, ya que estos son aquellos que ya están en la DB
            */

            // si el token es válido (no ha caducado o la firma es válida) para ESTE usuario
            if (jwtService.isTokenValid(token, userDetails)) {

                //si es válido, creo un objeto usuarioAutenticado, con los datos del usuario que intenta loguearse
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,

                        // en JWT no se necesita la contraseña después de la autenticación inicial.
                        null,

                        //obtengo los roles asociados al usuario
                        userDetails.getAuthorities());

                //Establezco detalles web adicionales, en este caso en base a la peticion HttpServletRequest
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //El usuario representado por este token ahora está autenticado y puede acceder a los recursos protegidos
                SecurityContextHolder.getContext().setAuthentication(authToken);
                //Le indico al contexto de seguridad que el usuario fue autenticado
            }

        }

        // si es nulo, que siga su ruta, y maneje el cuerpo de la peticion
        filterChain.doFilter(request, response);
    }

    //funcion interna para obtener el token de la peticion
    private String getTokenFromRequest(HttpServletRequest request) {

        //Desde la peticion, accedo a la autorizacion del header (que contiene el token)
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Filtramos la palabra Bearer que contiene el header, para obtener unicamente el token:

        // Clase de SpringUtils para trabajar con String.
        //Buscamos que, de todo el Header, vaya al auth, y que el authHeader contenga "Bearer", que es como inicia todo JWT

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            //Devolvemos un String a partir del caracter 7, para quitar la palabra Bearer y quedarnos solo con el token
            return authHeader.substring(7);
        }
        return null;
    }
    
}
