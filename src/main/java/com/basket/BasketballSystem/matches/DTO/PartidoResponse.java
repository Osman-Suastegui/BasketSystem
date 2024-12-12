package com.basket.BasketballSystem.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidoResponse {

    private Long clavePartido;
    private String Fase;
    private String fechaInicio;
    private String equipo1;
    private String equipo2;
    private String arbitro;
    private String resultado;
    private Long claveTemporada;
}
