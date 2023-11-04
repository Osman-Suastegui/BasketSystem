package com.basket.BasketballSystem.jugadores_equipos.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JugadoresEquipoDTO {
    private String equipoNombre;
    private String jugadorUsuario;
    private String posicion;
}
