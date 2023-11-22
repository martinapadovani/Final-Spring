package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Sede;

@Repository
public interface SedeRepositorio extends JpaRepository<Sede, Integer> {
    
}
