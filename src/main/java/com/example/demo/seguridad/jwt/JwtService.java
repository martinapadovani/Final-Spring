package com.example.demo.seguridad.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    //puede estar en un archivo .env
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    //GENERAR TOKEN

    // este método es el que invocaremos en el AuthService
    //Retorna el token, en base a un usuario dado
    public String getToken(UserDetails user) {
        //sobrecarga de metodos
        //Llamo a la funcion que crea un token, en base a un usuario dado
        return getToken(new HashMap<>(), user);
        //le paso un map vacio ya que no tenemos reclamos extras
    }

    // Crea un token, especificando todas sus partes (encabezado, carga útil y firma)
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        //Recibe los reclamos extras (si los hay), y el usuario

        // construimos el jwt
        return Jwts
                .builder()
                .setClaims(extraClaims)
                //Seteamos el usuario, en base al username del usuario recibido x parametro
                .setSubject(user.getUsername())
                // establece la fecha de emisión del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //Establezco una expiracion de 24horas despues
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                //Firmo el token utilizando la clave secreta y un algoritmo de firmas
                .signWith(getKey(), SignatureAlgorithm.HS256)
                //Recibe la funcion definida .encodeKey, que retorna la llave secreta encriptada

                /*Toma lo configurado/establecido anteriormente,
                lo compacta en una cadena de texto que se puede enviar y recibir
                mediante encabezados HTTP (el token)*/
                .compact();
    }

    //Encriptamos la llave secreta, para mas seguridad
    private Key getKey() {
        // convierte la SECRET_KEY en un arreglo de bytes, utilizando la decodificación BASE64.

        //primero se decodifica en bytes
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // construye una clave HMAC con el arreglo de bytes (la decodificacion)
        //hmacShaKeyFor crea una nueva instancia de la SECRET_KEY
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //OBTENER USERNAME DEL TOKEN

    // recibe un token como argumento, del cual extraerá el username
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
        //El argumento "Claims::getSubject" corresponde a la sintaxis de Function
        // Indica: de todas las Claims, traeme la de Subject (es decir, el username del token)
        //Con claim me refiero al "reclamo", lo que voy a reclamarle (obtener) del token
    } 

    //El método retorna un genérico
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        /*  Recibe por parámetro un reclamo: cuál? El que necesitemos, está definido como T (genérico), puede tomar cualquier valor.
            Sintaxis: Function<lo_que_recibe, lo_que_devuelve> nombre_de_la_function
            El argumento es una funcion declarada en el momneto.
            Objetivo del método: proporciona una forma genérica de extraer información de un token JWT
            Nos devuelve el reclamo que le pidamos cuando lo intanciemos, ya que esta preparado para devolver todos <T>
        */

        //Instancio un Claims a la que le asigno el valor de todos los Claims asociados al token
        final Claims claims = getAllClaims(token);

        // Retorno la Function del parámetro, cuyo primer valor es claims 
        return claimsResolver.apply(claims);
        // y el segundo valor dependerá de lo que se le pida cuando se la invoque:
        //En este caso se la invoca en  getUsernameFromToken: getClaim(token, Claims::getSubject), y le pide el subject.
    }

    //Obtiene todos los reclamos del token
    private Claims getAllClaims(String token) {
        return Jwts
                // crea un parser de JWT, que extrae la información contenida en el token, como los claims.
                .parserBuilder()
                //verifica la llave, que sea del mismo tipo que la que se envia, indicandole la estretegia que encripta la llave
                .setSigningKey(getKey())
                //Construye el objeto con el contenido del token
                .build()
                // Parsea el token JWT y verifica su firma
                .parseClaimsJws(token)
                // Obtiene el cuerpo del token, que contiene los claims
                .getBody();
    }

    //VERIFICAR SI EL TOKEN ES VALIDO

    //Para determinar si es válido, debemos saber si expiró o no
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        //Devuelve un booleano que verifica si el username de token es el mismo que el de la DB
        //Y verifica que el token no haya expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        // compara la fecha de expiración obtenida del token con la fecha y hora actual
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
    
}
