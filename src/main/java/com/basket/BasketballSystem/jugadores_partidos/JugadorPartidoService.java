package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.equipos.EquipoRepository;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class JugadorPartidoService {
    @Autowired
    JugadorPartidoRepository jugadorPartidoRepository;

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;


    public ResponseEntity<String> agregarJugadorPartido(JugadorPartido jugadorPartido) {

        if (jugadorPartido.getEquipo() == null) {
            return ResponseEntity.badRequest().body("El jugador debe pertenecer a un equipo");
        }
        if (jugadorPartido.getJugador() == null) {
            return ResponseEntity.badRequest().body("El jugador no puede ser nulo");
        }
        if(jugadorPartido.getPartido() == null){
            return ResponseEntity.badRequest().body("El partido del jugador no puede ser nula");
        }

        JugadoresEquipo jugadoresEquipo = jugadoresEquipoRepository.findByJugadorAndEquipo_Nombre(jugadorPartido.getJugador(), jugadorPartido.getEquipo());
        jugadorPartido.setPosicion(jugadoresEquipo.getPosicion());


        jugadorPartidoRepository.save(jugadorPartido);
        return ResponseEntity.ok("Jugador agregado al partido");
    }
}
