package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_partidos.DTO.ObtenerJugadoresDePartidoyEquipoResponse;
import com.basket.BasketballSystem.jugadores_partidos.DTO.ActualizarJugadorPartidoResponse;
import com.basket.BasketballSystem.jugadores_partidos.DTO.actualizarJugadorPartidoRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JugadorPartidoService {
    @Autowired
    JugadorPartidoRepository jugadorPartidoRepository;

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;


    public ResponseEntity<Map<String, Object>> agregarJugadorPartido(JugadorPartido jugadorPartido) {

        if (jugadorPartido.getEquipo() == null) {
            throw new BadRequestException("El equipo no puede ser nulo");
        }
        if (jugadorPartido.getJugador() == null) {
            throw new BadRequestException("El jugador no puede ser nulo");
        }
        if(jugadorPartido.getPartido() == null){
            throw new BadRequestException("El partido no puede ser nulo");
        }

        JugadoresEquipo jugadoresEquipo = jugadoresEquipoRepository.findByJugadorAndEquipo_Nombre(jugadorPartido.getJugador(), jugadorPartido.getEquipo());
        jugadorPartido.setPosicion(jugadoresEquipo.getPosicion());
        jugadorPartido.setAsistencias(0);
        jugadorPartido.setAnotaciones(0);
        jugadorPartido.setTirosDe2Puntos(0);
        jugadorPartido.setTirosLibres(0);
        jugadorPartido.setTirosDe3Puntos(0);
        jugadorPartido.setFaltas(0);
        jugadorPartido.setEnBanca(true);

        jugadorPartidoRepository.save(jugadorPartido);

        Map<String, Object> jugadorpart = new HashMap<>();
        jugadorpart.put("message", "Jugador agregado al partido");


        return ResponseEntity.ok(jugadorpart);
    }

    public ActualizarJugadorPartidoResponse agregarPunto(actualizarJugadorPartidoRequest jugadorPartidoDTO) {

        String jugadorUsuario = jugadorPartidoDTO.getJugador();
        Long jugadorPartidoId = jugadorPartidoDTO.getClavePartido();
        String descripcion = jugadorPartidoDTO.getDescripcion();

        JugadorPartido jugadorPartido = jugadorPartidoRepository.findByJugadorAndPartido(jugadorUsuario, jugadorPartidoId);
        ActualizarJugadorPartidoResponse actJugPartido = new ActualizarJugadorPartidoResponse();

        switch (descripcion) {
            case "tirosDe2Puntos" -> {
                jugadorPartido.setTirosDe2Puntos(jugadorPartido.getTirosDe2Puntos() + 1);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + 1);
                actJugPartido.setDescripcion("tirosDe2Puntos");
            }
            case "tirosDe3Puntos" -> {
                jugadorPartido.setTirosDe3Puntos(jugadorPartido.getTirosDe3Puntos() + 1);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + 1);
                actJugPartido.setDescripcion("tirosDe3Puntos");
            }
            case "tirosLibres" -> {
                jugadorPartido.setTirosLibres(jugadorPartido.getTirosLibres() + 1);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + 1);
                actJugPartido.setDescripcion("tirosLibres");
            }
            case "faltas" -> {
                jugadorPartido.setFaltas(jugadorPartido.getFaltas() + 1);
                actJugPartido.setDescripcion("faltas");
            }
            case "asistencias" -> {
                jugadorPartido.setAsistencias(jugadorPartido.getAsistencias() + 1);
                actJugPartido.setDescripcion("asistencias");
            }
        }

        jugadorPartidoRepository.save(jugadorPartido);

        actJugPartido.setJugador(jugadorPartido.getJugador().getUsuario());


        return actJugPartido;


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

    public List<String> obtenerJugadoresNoEnPartido(String nombreEquipo, Long clavePartido) {
        List<String> jugadoresNoEnPartido = new ArrayList<>();
        jugadoresNoEnPartido = jugadoresEquipoRepository.findJugadoresNoEnPartidos(nombreEquipo, clavePartido);
        return jugadoresNoEnPartido;
    }
}
