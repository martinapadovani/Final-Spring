package com.example.demo.enumeradores;

import com.example.demo.exception.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Horarios {

    MAÑANA,
    TARDE,
    NOCHE;

    @JsonCreator
    public static Horarios fromString(String horario) {
        try {
            return Horarios.valueOf(horario.toUpperCase());
        } catch (Exception e) {
           throw new BadRequestException("El valor del horario no es válido. Horario: " + horario);
        }
    }
}