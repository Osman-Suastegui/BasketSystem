package com.basket.BasketballSystem.matches_player.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarJugadorPartidoResponse {

    private String jugador;
    private String descripcion;
    private boolean puntoPositivo;

}
