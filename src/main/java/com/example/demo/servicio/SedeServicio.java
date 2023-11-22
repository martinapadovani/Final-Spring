package com.example.demo.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Sede;
import com.example.demo.repositorio.SedeRepositorio;

@Service
public class SedeServicio {
    
    @Autowired
    SedeRepositorio sedeRepositorio;

    
    @Transactional
    public List<Sede> obtenerSedes(){
        return sedeRepositorio.findAll();
    }

    @Transactional
    public void agregarSede(Sede nuevaSede){
        sedeRepositorio.save(nuevaSede);
    }

    @Transactional
    public void actualizarSede(){

    }

    @Transactional
    public void borrarSede(){

    }
    
}
