package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_partidos.DTO.ObtenerJugadoresDePartidoyEquipoResponse;
import com.basket.BasketballSystem.jugadores_partidos.DTO.actualizarJugadorPartidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
        jugadorPartido.setAsistencias(0);
        jugadorPartido.setAnotaciones(0);
        jugadorPartido.setTirosDe2Puntos(0);
        jugadorPartido.setTirosLibres(0);
        jugadorPartido.setTirosDe3Puntos(0);
        jugadorPartido.setFaltas(0);

        jugadorPartidoRepository.save(jugadorPartido);
        return ResponseEntity.ok("Jugador agregado al partido");
    }

    public ResponseEntity<String> actualizarJugadorPartido(actualizarJugadorPartidoDTO jugadorPartidoDTO) {

        String jugadorUsuario = jugadorPartidoDTO.getJugador();
        Long jugadorPartidoId = jugadorPartidoDTO.getClavePartido();
        String descripcion = jugadorPartidoDTO.getDescripcion();

        JugadorPartido jugadorPartido = jugadorPartidoRepository.findByJugadorAndPartido(jugadorUsuario, jugadorPartidoId);

        switch (descripcion) {
            case "tirosDe2Puntos" -> {
                jugadorPartido.setTirosDe2Puntos(jugadorPartido.getTirosDe2Puntos() + 1);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + 1);
            }
            case "tirosDe3Puntos" -> {
                jugadorPartido.setTirosDe3Puntos(jugadorPartido.getTirosDe3Puntos() + 1);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + 1);
            }
            case "tirosLibres" -> {
                jugadorPartido.setTirosLibres(jugadorPartido.getTirosLibres() + 1);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + 1);
            }
            case "faltas" -> jugadorPartido.setFaltas(jugadorPartido.getFaltas() + 1);
            case "asistencias" -> jugadorPartido.setAsistencias(jugadorPartido.getAsistencias() + 1);
        }

        jugadorPartidoRepository.save(jugadorPartido);
        return ResponseEntity.ok("Jugador agregado al partido");

    }

    public List<ObtenerJugadoresDePartidoyEquipoResponse> obtenerJugadoresDePartidoyEquipo(String nombreEquipo,Long clavePartido,Boolean enBancaFiltro){
      List<JugadorPartido> jugadorPartido = new ArrayList<>();
        List<ObtenerJugadoresDePartidoyEquipoResponse> jugadoresPartidoResponse = new ArrayList<>();
        jugadorPartido = jugadorPartidoRepository.findAllByEquipoAndPartidoAndNombre2(nombreEquipo,clavePartido,enBancaFiltro);

        for (JugadorPartido jp: jugadorPartido) {
            ObtenerJugadoresDePartidoyEquipoResponse jpResponse = new ObtenerJugadoresDePartidoyEquipoResponse();

            jpResponse.setJugador(jp.getJugador().getUsuario());
            jpResponse.setTirosDe3Puntos(jp.getTirosDe3Puntos());
            jpResponse.setTirosDe2Puntos(jp.getTirosDe2Puntos());
            jpResponse.setTirosLibres(jp.getTirosLibres());
            jpResponse.setFaltas(jp.getFaltas());
            jpResponse.setAsistencias(jp.getAsistencias());

            jugadoresPartidoResponse.add(jpResponse);

        }


        return jugadoresPartidoResponse;

    }
}
