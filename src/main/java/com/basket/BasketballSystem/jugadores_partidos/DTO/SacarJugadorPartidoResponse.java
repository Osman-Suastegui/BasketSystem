package com.basket.BasketballSystem.jugadores_partidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SacarJugadorPartidoResponse {
    String jugador;
    String nombreEquipo;
    Long clavePartido;
}
