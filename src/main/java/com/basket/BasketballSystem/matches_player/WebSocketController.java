package com.basket.BasketballSystem.matches_player;

import com.basket.BasketballSystem.matches_player.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class WebSocketController {

    @Autowired
    MatchPlayerService matchPlayerService;
    @MessageMapping("/actualizarPunto/{claveDelPartido}")
    @SendTo("/topic/ActualizacionesDePuntos/{claveDelPartido}")
    public ActualizarJugadorPartidoResponse actualizarPunto(actualizarJugadorPartidoRequest actualizarJugadorPartidoMensaje) {
        return matchPlayerService.actualizarPunto(actualizarJugadorPartidoMensaje);
    }

    @MessageMapping("/sacarJugador/{claveDelPartido}")
    @SendTo("/topic/sacarJugador/{claveDelPartido}")
    public SacarJugadorPartidoResponse sacarJugadorDePartido(SacarJugadorDePartidoRequest sacarJugadorDePartidoMensaje) {
        System.out.println("sacarJugadorDePartidoMensaje = " + sacarJugadorDePartidoMensaje);
        return matchPlayerService.sacarJugadorDePartido(sacarJugadorDePartidoMensaje);
    }

    @MessageMapping("/meterJugador/{claveDelPartido}")
    @SendTo("/topic/meterJugador/{claveDelPartido}")
    public MeterJugadorResponse meterJugadorAPartido(MeterJugadorRequest meterJugadorMessage) {
        System.out.println("sacarJugadorDePartidoMensaje = " + meterJugadorMessage);
        return matchPlayerService.meterJugadorAPartido(meterJugadorMessage);
    }

}
