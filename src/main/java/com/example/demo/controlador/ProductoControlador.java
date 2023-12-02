package com.example.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @GetMapping("/")
    public String index(){
        return "index-productos.html";  
    }
    
}
