package com.example.demo.servicio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Sede;
import com.example.demo.enumeradores.Horarios;
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
    public List<Sede> obtenerSedesPorZona(String zona){
        return sedeRepositorio.findByZona(zona);
    }

    @Transactional
    public void agregarSede(Sede nuevaSede){
        sedeRepositorio.save(nuevaSede);
    }

    @Transactional
    public void actualizarSede(String id, String zona, String direccion, Horarios horarioVenta){

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);

        Sede sede = sedeOptional.get();

        sede.setZona(zona);
        sede.setDireccion(direccion);
        sede.setHorarioVenta(horarioVenta);

        sedeRepositorio.save(sede);

    }

    @Transactional
    public void borrarSede(String id){

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);

        Sede sede = sedeOptional.get();

        sedeRepositorio.delete(sede);
    }
    
}
