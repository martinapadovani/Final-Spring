package com.example.demo.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sede")
public class SedeControlador {

    @GetMapping()
    public String index(){
        return "index-sede.html";
    }
    
}
