package com.basket.BasketballSystem.matches_player.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeterJugadorRequest {

    String jugador;
    String nombreEquipo;
    Long clavePartido;
    Integer tirosDe3Puntos;
    Integer tirosDe2Puntos;
    Integer tirosLibres;
    Integer faltas;
    Integer asistencias;

}
