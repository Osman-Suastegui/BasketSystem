package com.basket.BasketballSystem.jugadores_partidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class JugadorPartidoService {
    @Autowired
    JugadorPartidoRepository jugadorPartidoRepository;


    public ResponseEntity<String> agregarJugadorPartido(JugadorPartido jugadorPartido) {

        if (jugadorPartido.getEquipo() == null) {
            return ResponseEntity.badRequest().body("El jugador debe pertenecer a un equipo");
        }
        if (jugadorPartido.getJugador() == null) {
            return ResponseEntity.badRequest().body("El jugador no puede ser nulo");
        }
        if(jugadorPartido.getPosicion() == null){
            return ResponseEntity.badRequest().body("La posición del jugador no puede ser nula");
        }
        if(jugadorPartido.getPartido() == null){
            return ResponseEntity.badRequest().body("El partido no puede ser nulo");
        }

        jugadorPartidoRepository.save(jugadorPartido);
        return ResponseEntity.ok("Jugador agregado al partido");
    }
}
