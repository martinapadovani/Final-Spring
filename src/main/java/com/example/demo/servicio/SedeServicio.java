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

        if (!(sede.getNombre() instanceof String) || sede.getNombre() == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + sede.getNombre());
        }
        if(sedeRepositorio.findByNombre(sede.getNombre()).isPresent()) {
            throw new NotFoundException("Ya existe una sede con ese nombre! Nombre: " + sede.getNombre());
        }
        if (!(sede.getZona() instanceof String) || sede.getZona() == null) {
            throw new BadRequestException("El tipo de zona no es válida. Zona: " + sede.getZona());
        }
        if (!(sede.getDireccion() instanceof String) || sede.getDireccion() == null) {
            throw new BadRequestException("El tipo de dirección no es válida. Dirección: " + sede.getDireccion());
        }
        if (sede.getHorarioVenta() == null) {
            throw new BadRequestException("El tipo de horario no es válido. Horario: " + sede.getHorarioVenta());
        }

        if (!(sede.getHorarioVenta() instanceof Horarios)) {
            String horarioMayusculas = sede.getHorarioVenta().toString().toUpperCase();
            Horarios horarioEnum = Horarios.valueOf(horarioMayusculas);
            sede.setHorarioVenta(horarioEnum);
        }

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
    public Sede obtenerSedePorId(String id){

        if (!(id instanceof String) || id == null) {
            throw new BadRequestException("El tipo de ID no es válido. Se esperaba un String. ID: " + id);
        }
        if(sedeRepositorio.findById(id).isEmpty()) {
            throw new NotFoundException("La sede solicitado no existe. ID: " + id);
        }

        return sedeRepositorio.findById(id).get();
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
    public String actualizarSede(String nombre, Sede sede){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + nombre);
        }
        if(!(sedeRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("La sede solicitado no existe. Nombre: " + nombre);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombre);
        Sede sedeDB = sedeOptional.get();
        Sede sedeActualizada = sede;

        if(!(sedeActualizada.getNombre() == null)){ //Si lo deja en null es porque no quiere cambiarlo. En ese cambio no se actualiza.
            sedeDB.setNombre(sedeActualizada.getNombre());
        }
        if(!(sedeActualizada.getZona() == null)){
            sedeDB.setZona(sedeActualizada.getZona());
        }
       if(!(sedeActualizada.getDireccion() == null)){
            sedeDB.setDireccion(sedeActualizada.getDireccion());
       } 
       if(!(sede.getHorarioVenta() == null)){
            sedeDB.setHorarioVenta(sede.getHorarioVenta());
       }
    
        sedeRepositorio.save(sedeDB);

        return "Sede actualizada exitosamente!";
    }

    @Transactional
    public String actualizarNombre(String nombre, String nuevoNombre){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + nombre);
        }
        if (!(nuevoNombre instanceof String) || nuevoNombre == null) {
            throw new BadRequestException("El tipo de nombre no es válido. Nombre: " + nuevoNombre);
        }
        if(!(sedeRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("La sede solicitado no existe. Nombre: " + nombre);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombre);
        Sede sede = sedeOptional.get();

        sede.setNombre(nombre);
        sedeRepositorio.save(sede);

        return "Zona actualizada con éxito!";
    }

    @Transactional
    public String actualizarZona(String nombre, String zona){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + nombre);
        }
        if (!(zona instanceof String) || zona == null) {
            throw new BadRequestException("El tipo de zona no es válida. Zona: " + zona);
        }
        if(!(sedeRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("La sede solicitado no existe. Nombre: " + nombre);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombre);
        Sede sede = sedeOptional.get();

        sede.setZona(zona);
        sedeRepositorio.save(sede);

        return "Zona actualizada con éxito!";
    }

    @Transactional
    public String actualizarDireccion(String nombre, String direccion){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + nombre);
        }
        if (!(direccion instanceof String) || direccion == null) {
            throw new BadRequestException("El tipo de dirección no es válida. Dirección: " + direccion);
        }
        if(!(sedeRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("La sede solicitado no existe. Nombre: " + nombre);
        }
        
        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombre);
        Sede sede = sedeOptional.get();

        sede.setDireccion(direccion);
        sedeRepositorio.save(sede);

        return "Dirección actualizada con éxito!";
    }

    @Transactional
    public String actualizarHorario(String nombre, String horario){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + nombre);
        }
        if (!(horario instanceof String) || horario == null) {
            throw new BadRequestException("El tipo de horario no es válido. Horario: " + horario);
        }
        if(!(sedeRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("La sede solicitado no existe. Nombre: " + nombre);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombre);
        Sede sede = sedeOptional.get();

        String horarioMayusculas = horario.toUpperCase();
        Horarios horarioEnum = Horarios.valueOf(horarioMayusculas);

        sede.setHorarioVenta(horarioEnum);
        sedeRepositorio.save(sede);

        return "Horario actualizado con éxito!";  
    }

    @Transactional
    public String borrarSede(String nombre){

        if (!(nombre instanceof String) || nombre == null) {
            throw new BadRequestException("El valor del nombre no es válido. Nombre: " + nombre);
        }
        if(!(sedeRepositorio.findByNombre(nombre).isPresent())) {
            throw new NotFoundException("La sede solicitado no existe. Nombre: " + nombre);
        }

        Optional<Sede> sedeOptional = sedeRepositorio.findByNombre(nombre);
        Sede sede = sedeOptional.get();
        sedeRepositorio.delete(sede);

        return "La sede "  + sede.getNombre() + ", ha sido eliminada exitosamente! ";
    }
    
}
