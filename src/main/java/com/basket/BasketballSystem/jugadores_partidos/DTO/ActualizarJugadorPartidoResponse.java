package com.basket.BasketballSystem.jugadores_partidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarJugadorPartidoResponse {

    String jugador;
    String descripcion;
}
