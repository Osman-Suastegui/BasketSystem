package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.jugadores_partidos.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller

public class WebSocketController {

    @Autowired
    JugadorPartidoService jugadorPartidoService;
    @MessageMapping("/actualizarPunto/{claveDelPartido}")
    @SendTo("/topic/ActualizacionesDePuntos/{claveDelPartido}")
    public ActualizarJugadorPartidoResponse actualizarPunto(actualizarJugadorPartidoRequest actualizarJugadorPartidoMensaje) {
        return jugadorPartidoService.actualizarPunto(actualizarJugadorPartidoMensaje);
    }

    @MessageMapping("/sacarJugador/{claveDelPartido}")
    @SendTo("/topic/sacarJugador/{claveDelPartido}")
    public SacarJugadorPartidoResponse sacarJugadorDePartido(SacarJugadorDePartidoRequest sacarJugadorDePartidoMensaje) {
        System.out.println("sacarJugadorDePartidoMensaje = " + sacarJugadorDePartidoMensaje);
        return jugadorPartidoService.sacarJugadorDePartido(sacarJugadorDePartidoMensaje);
    }

    @MessageMapping("/meterJugador/{claveDelPartido}")
    @SendTo("/topic/meterJugador/{claveDelPartido}")
    public MeterJugadorResponse meterJugadorAPartido(MeterJugadorRequest meterJugadorMessage) {
        System.out.println("sacarJugadorDePartidoMensaje = " + meterJugadorMessage);
        return jugadorPartidoService.meterJugadorAPartido(meterJugadorMessage);
    }

}
