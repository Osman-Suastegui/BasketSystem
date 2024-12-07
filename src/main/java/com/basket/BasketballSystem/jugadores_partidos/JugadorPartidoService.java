package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_partidos.DTO.*;

import jakarta.transaction.Transactional;
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

        JugadoresEquipo jugadoresEquipo = jugadoresEquipoRepository.findByJugadorAndTeam_Nombre(jugadorPartido.getJugador(), jugadorPartido.getEquipo());
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

    public ActualizarJugadorPartidoResponse actualizarPunto(actualizarJugadorPartidoRequest jugadorPartidoDTO) {

        String jugadorUsuario = jugadorPartidoDTO.getJugador();
        Long jugadorPartidoId = jugadorPartidoDTO.getClavePartido();
        String descripcion = jugadorPartidoDTO.getDescripcion();

        JugadorPartido jugadorPartido = jugadorPartidoRepository.findByJugadorAndPartido(jugadorUsuario, jugadorPartidoId);
        ActualizarJugadorPartidoResponse actJugPartido = new ActualizarJugadorPartidoResponse();
        actJugPartido.setJugador(jugadorPartido.getJugador().getUsuario());
        actJugPartido.setPuntoPositivo(jugadorPartidoDTO.isPuntoPositivo());
        int punto = 1;
        if(!jugadorPartidoDTO.isPuntoPositivo()) punto = - 1;

        switch (descripcion) {
            case "tirosDe2Puntos" -> {
                actJugPartido.setDescripcion("tirosDe2Puntos");

                if (jugadorPartido.getTirosDe2Puntos() <= 0 && !jugadorPartidoDTO.isPuntoPositivo()) {
                    actJugPartido.setDescripcion("");
                    return actJugPartido;
                }
                jugadorPartido.setTirosDe2Puntos(jugadorPartido.getTirosDe2Puntos() + punto );
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + punto );
            }
            case "tirosDe3Puntos" -> {
                actJugPartido.setDescripcion("tirosDe3Puntos");

                if (jugadorPartido.getTirosDe3Puntos() <= 0 && !jugadorPartidoDTO.isPuntoPositivo()) {
                    actJugPartido.setDescripcion("");

                    return actJugPartido;
                }
                jugadorPartido.setTirosDe3Puntos(jugadorPartido.getTirosDe3Puntos() + punto );
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + punto );
            }
            case "tirosLibres" -> {
                actJugPartido.setDescripcion("tirosLibres");

                if (jugadorPartido.getTirosLibres() <= 0 && !jugadorPartidoDTO.isPuntoPositivo()) {
                    actJugPartido.setDescripcion("");

                    return actJugPartido;
                }
                jugadorPartido.setTirosLibres(jugadorPartido.getTirosLibres() + punto);
                jugadorPartido.setAnotaciones(jugadorPartido.getAnotaciones() + punto);
            }
            case "faltas" -> {
                actJugPartido.setDescripcion("faltas");

                if (jugadorPartido.getFaltas() <= 0 && !jugadorPartidoDTO.isPuntoPositivo()) {
                    actJugPartido.setDescripcion("");

                    return actJugPartido;
                }
                jugadorPartido.setFaltas(jugadorPartido.getFaltas() + punto);
            }
            case "asistencias" -> {
                actJugPartido.setDescripcion("asistencias");

                if (jugadorPartido.getAsistencias() <= 0 && !jugadorPartidoDTO.isPuntoPositivo()) {
                    actJugPartido.setDescripcion("");

                    return actJugPartido;
                }
                jugadorPartido.setAsistencias(jugadorPartido.getAsistencias() + punto);
            }
        }

        jugadorPartidoRepository.save(jugadorPartido);



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
            jpResponse.setEnBanca(jp.getEnBanca());

            jugadoresPartidoResponse.add(jpResponse);

        }


        return jugadoresPartidoResponse;

    }

    public List<String> obtenerJugadoresNoEnPartido(String nombreEquipo, Long clavePartido, String nombreEquipo2) {
        List<String> jugadoresNoEnPartido = new ArrayList<>();
        jugadoresNoEnPartido = jugadoresEquipoRepository.findJugadoresNoEnPartidos(nombreEquipo, clavePartido, nombreEquipo2);
        return jugadoresNoEnPartido;
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> posicionarJugadorEnPartido(Long clavePartido, String usuario, Boolean enBanca) {
        jugadorPartidoRepository.posicionarJugadorEnPartido(clavePartido,usuario,enBanca);
        Map<String, Object> jugadorpart = new HashMap<>();
        jugadorpart.put("message", "Jugador actualizado");
        return ResponseEntity.ok(jugadorpart);
    }

    @Transactional
    public SacarJugadorPartidoResponse sacarJugadorDePartido(SacarJugadorDePartidoRequest sacarJugadorDePartidoMensaje) {
        Long clavePartido = sacarJugadorDePartidoMensaje.getClavePartido();
        String jugador = sacarJugadorDePartidoMensaje.getJugador();
        String nombreEquipo = sacarJugadorDePartidoMensaje.getNombreEquipo();
        jugadorPartidoRepository.setEnBanca(true,clavePartido,jugador,nombreEquipo);

        SacarJugadorPartidoResponse sacarJugadorPartidoResponse = new SacarJugadorPartidoResponse();
        sacarJugadorPartidoResponse.setClavePartido(clavePartido);
        sacarJugadorPartidoResponse.setJugador(jugador);
        sacarJugadorPartidoResponse.setNombreEquipo(nombreEquipo);
        return sacarJugadorPartidoResponse;


    }

    @Transactional
    public MeterJugadorResponse meterJugadorAPartido(MeterJugadorRequest meterJugadorMessage) {
        Long clavePartido = meterJugadorMessage.getClavePartido();
        String jugador = meterJugadorMessage.getJugador();
        String nombreEquipo = meterJugadorMessage.getNombreEquipo();

        jugadorPartidoRepository.setEnBanca(false,clavePartido,jugador,nombreEquipo);

        MeterJugadorResponse meterJugadorResponse = new MeterJugadorResponse();
        meterJugadorResponse.setClavePartido(clavePartido);
        meterJugadorResponse.setJugador(jugador);
        meterJugadorResponse.setNombreEquipo(nombreEquipo);
        meterJugadorResponse.setTirosDe3Puntos(meterJugadorMessage.getTirosDe3Puntos());
        meterJugadorResponse.setTirosDe2Puntos(meterJugadorMessage.getTirosDe2Puntos());
        meterJugadorResponse.setTirosLibres(meterJugadorMessage.getTirosLibres());
        meterJugadorResponse.setFaltas(meterJugadorMessage.getFaltas());
        meterJugadorResponse.setAsistencias(meterJugadorMessage.getAsistencias());
        return meterJugadorResponse;
    }

    public int sumarPuntosPorEquipoYPartido(String nombreEquipo, Long clavePartido){
        return jugadorPartidoRepository.sumarPuntosPorEquipoYPartido(nombreEquipo,clavePartido);
    }
}
