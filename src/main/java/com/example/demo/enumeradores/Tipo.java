package com.example.demo.enumeradores;

import com.example.demo.exception.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Tipo {

    VERDURA,
    FRUTA;

    @JsonCreator
    public static Tipo fromString(String tipo) {
        try {
            return Tipo.valueOf(tipo.toUpperCase());
        } catch (Exception e) {
           throw new BadRequestException("El valor del tipo no es válido. Tipo: " + tipo);
        }
    }
}
