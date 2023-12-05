package com.example.demo.seguridad.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.seguridad.jwt.JwtService;
import com.example.demo.seguridad.user.Role;
import com.example.demo.seguridad.user.User;
import com.example.demo.seguridad.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;

        // declaramos un AuthenticationManager para administrar los filtros del login
        private final AuthenticationManager authenticationManager;

        //LOGIN

        //Recibimos por parámetro los datos del login con el modelo LoginRequest creado por nosotros
        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                                /*Recibe por parametro un nuevo UsernamePasswordAuthenticationToken que se construye 
                                 * con los datos recibidos desde la peticion */

                //El nuevo UsernamePasswordAuthenticationToken será validado por el AuthenticationProvider
                // en caso de éxito, avanza hacia la generación del token:

                // para generar el token, Security precisa trabajar con el UserDetails,
                // que tiene detalles de la sesión(rol del usuario), que también pasan por un filtro de seguridad 
                UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
                //Busco mi usuario en mi DB y lo guardo en una variable user

                //creamos un nuevo token en base a los datos del usuario
                String token = jwtService.getToken(user);
                return AuthResponse.builder()
                                .token(token)
                                .build();

        }

        //REGISTER

        // Creamos un metodo (servicio) que realiza el registro, en base a la peticion de registro recibida
        public AuthResponse register(RegisterRequest request) {
                //Retorna AuthResponse que configuramos nosotros, el modelo de respuesta que incluye el token

                //Instancia un usuario
                User user = User.builder()
                //Podemos acceder al metodo .builder gracias al bean @Builder que está en la entidad
                //Construimos un usuario con los valores que llegaron desde la peticion (RegisterRequest)
                                .username(request.getUsername())//completamos el atributo username con el username dentro de la peticion
                                .password(passwordEncoder.encode(request.getPassword()))
                                //Desde el objeto passwordEncoder importado, accedemos al metodo .encode para encriptar la contraseña
                                .firstname(request.getFirstname())
                                .lastname(request.lastname)
                                .country(request.getCountry())
                                .role(Role.USER)
                                //indicamos que cada vez que un usuario se registre, por defecto, sea de tipo USER
                                .build();

                // guardamos al usuario en la DB
                userRepository.save(user);

                ///una vez registrado el usuario, le envío el token, para que pueda ingresar.
                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                //Desde jwt ejecutamos la funcion que definimos para obtener el token de un usuario
                                .build();

        }
    
}
