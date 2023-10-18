package com.basket.BasketballSystem.jugadores_partidos.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class actualizarJugadorPartidoDTO {

    @NotBlank(message = "la clave del partido no puede estar en blanco")
    private Long clavePartido;
    @NotBlank(message = "el jugador no puede estar en blanco")
    private String jugador;

    @NotBlank(message = "la descripcion no puede estar en blanco")
//   tipo de estadistica de juego que se le actualiza al jugador
    private String descripcion;

}
