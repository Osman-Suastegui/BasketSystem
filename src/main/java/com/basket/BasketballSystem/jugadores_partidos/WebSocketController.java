package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.jugadores_partidos.DTO.ActualizarJugadorPartidoResponse;
import com.basket.BasketballSystem.jugadores_partidos.DTO.actualizarJugadorPartidoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller

public class WebSocketController {

    @Autowired
    JugadorPartidoService jugadorPartidoService;
    @MessageMapping("/agregarPunto")
    @SendTo("/topic/ActualizacionesDePuntos")
    public ActualizarJugadorPartidoResponse agregarPunto(actualizarJugadorPartidoRequest actualizarJugadorPartidoMensaje) {
        return jugadorPartidoService.agregarPunto(actualizarJugadorPartidoMensaje);
    }
}
