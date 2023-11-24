package com.basket.BasketballSystem.temporadas.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TemporadaRequest {
    private Long claveTemporada;
    private Integer cantidadEquipos;
    private Integer cantidadPlayoffs;
    private Integer cantidadEnfrentamientosRegular;

    // Getters y Setters
}

