package com.basket.BasketballSystem.matches_player.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ObtenerJugadoresDePartidoyEquipoResponse {

    String jugador;
    int asistencias;
    int faltas;
    int tirosDe2Puntos;
    int tirosDe3Puntos;
    int tirosLibres;
    Boolean enBanca;

}
