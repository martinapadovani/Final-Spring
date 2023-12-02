package com.example.demo.servicio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Sede;
import com.example.demo.enumeradores.Horarios;
import com.example.demo.exception.exceptions.BadRequestException;
import com.example.demo.exception.exceptions.NotFoundException;
import com.example.demo.repositorio.SedeRepositorio;

@Service
public class SedeServicio {
    
    @Autowired
    SedeRepositorio sedeRepositorio;

    @Transactional
    public String agregarSede(Sede sede){

        try{
            sedeRepositorio.save(sede);
            return "Sede creada con éxito!";
        }catch(Exception e){
            return e.getMessage();
        }
    }
    
    @Transactional
    public List<Sede> obtenerSedes(){

        if(sedeRepositorio.findAll().isEmpty()){
            throw new NotFoundException("No se encontraron sedes en al DB");
        }
        return sedeRepositorio.findAll();
    }

    @Transactional
    public Optional<Sede> obtenerSedePorId(String id){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        return sedeRepositorio.findById(id);
    }

    @Transactional
    public List<Sede> obtenerSedesPorZona(String zona){

        if (!(zona instanceof String) || zona == null) {
            throw new BadRequestException("El tipo de zona no es válida. Zona: " + zona);
        }
        if(sedeRepositorio.findByZona(zona).isEmpty()) {
            throw new NotFoundException("No existe ninguna sede asociada a esa zona. Zona: " + zona);
        }

        return sedeRepositorio.findByZona(zona);
    }

    @Transactional
    public String actualizarSede(String id, String nombre, String zona, String direccion, String horarioVenta){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El tipo de nombre no es válido. Nombre: " + nombre);
        }
        if (!(zona instanceof String) || zona == null) {
            throw new BadRequestException("El tipo de zona no es válida. Zona: " + zona);
        }
        if (!(direccion instanceof String) || direccion == null) {
            throw new BadRequestException("El tipo de dirección no es válida. Dirección: " + direccion);
        }
        if (!(horarioVenta instanceof String) || horarioVenta == null) {
            throw new BadRequestException("El tipo de horario no es válido. Horario: " + horarioVenta);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);
        Sede sede = sedeOptional.get();

        String horarioMayusculas = horarioVenta.toUpperCase();
        Horarios horarioEnum = Horarios.valueOf(horarioMayusculas);

        sede.setNombre(nombre);
        sede.setZona(zona);
        sede.setDireccion(direccion);
        sede.setHorarioVenta(horarioEnum);

        sedeRepositorio.save(sede);

        return "Sede actualizada exitosamente!";
    }

    @Transactional
    public String actualizarNombre(String id, String nombre){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El tipo de nombre no es válido. Nombre: " + nombre);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);
        Sede sede = sedeOptional.get();

        sede.setNombre(nombre);
        sedeRepositorio.save(sede);

        return "Zona actualizada con éxito!";
    }

    @Transactional
    public String actualizarZona(String id, String zona){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(zona instanceof String) || zona == null) {
            throw new BadRequestException("El tipo de zona no es válida. Zona: " + zona);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);
        Sede sede = sedeOptional.get();

        sede.setZona(zona);
        sedeRepositorio.save(sede);

        return "Zona actualizada con éxito!";
    }

    @Transactional
    public String actualizarDireccion(String id, String direccion){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(direccion instanceof String) || direccion == null) {
            throw new BadRequestException("El tipo de dirección no es válida. Dirección: " + direccion);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }
        
        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);
        Sede sede = sedeOptional.get();

        sede.setDireccion(direccion);
        sedeRepositorio.save(sede);

        return "Dirección actualizada con éxito!";
    }

    @Transactional
    public String actualizarHorario(String id, String horario){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if (!(horario instanceof String) || horario == null) {
            throw new BadRequestException("El tipo de horario no es válido. Horario: " + horario);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);
        Sede sede = sedeOptional.get();

        String horarioMayusculas = horario.toUpperCase();
        Horarios horarioEnum = Horarios.valueOf(horarioMayusculas);

        sede.setHorarioVenta(horarioEnum);
        sedeRepositorio.save(sede);

        return "Horario actualizado con éxito!";  
    }

    @Transactional
    public String borrarSede(String id){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findById(id);
        Sede sede = sedeOptional.get();
        sedeRepositorio.delete(sede);

        return "La sede "  + sede.getNombre() + ", ha sido eliminada exitosamente! ";
    }
    
}
