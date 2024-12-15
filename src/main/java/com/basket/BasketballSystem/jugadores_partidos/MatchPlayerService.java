package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.TeamPlayer;
import com.basket.BasketballSystem.jugadores_equipos.TeamPlayerRepository;
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
public class MatchPlayerService {
    @Autowired
    MatchPlayerRepository matchPlayerRepository;

    @Autowired
    TeamPlayerRepository teamPlayerRepository;


    public ResponseEntity<Map<String, Object>> agregarJugadorPartido(MatchPlayer matchPlayer) {

        if (matchPlayer.getEquipo() == null) {
            throw new BadRequestException("El equipo no puede ser nulo");
        }
        if (matchPlayer.getJugador() == null) {
            throw new BadRequestException("El jugador no puede ser nulo");
        }
        if(matchPlayer.getPartido() == null){
            throw new BadRequestException("El partido no puede ser nulo");
        }

        TeamPlayer teamplayers = teamPlayerRepository.findByPlayerAndTeam_Name(matchPlayer.getJugador(), matchPlayer.getEquipo());
        matchPlayer.setPosicion(teamplayers.getPosition());
        matchPlayer.setAsistencias(0);
        matchPlayer.setAnotaciones(0);
        matchPlayer.setFaltas(0);
        matchPlayer.setEnBanca(true);

        matchPlayerRepository.save(matchPlayer);

        Map<String, Object> jugadorpart = new HashMap<>();
        jugadorpart.put("message", "Jugador agregado al partido");


        return ResponseEntity.ok(jugadorpart);
    }

    public ActualizarJugadorPartidoResponse actualizarPunto(actualizarJugadorPartidoRequest jugadorPartidoDTO) {

        String jugadorUsuario = jugadorPartidoDTO.getJugador();
        Long jugadorPartidoId = jugadorPartidoDTO.getClavePartido();
        String descripcion = jugadorPartidoDTO.getDescripcion();

        MatchPlayer matchPlayer = matchPlayerRepository.findByJugadorAndMatch(jugadorUsuario, jugadorPartidoId);
        ActualizarJugadorPartidoResponse actJugPartido = new ActualizarJugadorPartidoResponse();
        actJugPartido.setJugador(matchPlayer.getJugador().getUsuario());
        actJugPartido.setPuntoPositivo(jugadorPartidoDTO.isPuntoPositivo());
        int punto = 1;
        if(!jugadorPartidoDTO.isPuntoPositivo()) punto = - 1;

        matchPlayerRepository.save(matchPlayer);

        return actJugPartido;


    }

    public List<ObtenerJugadoresDePartidoyEquipoResponse> obtenerJugadoresDePartidoyEquipo(String nombreEquipo,Long clavePartido,Boolean enBancaFiltro){
      List<MatchPlayer> matchPlayer = new ArrayList<>();
        List<ObtenerJugadoresDePartidoyEquipoResponse> jugadoresPartidoResponse = new ArrayList<>();
        matchPlayer = matchPlayerRepository.findAllByEquipoAndMatchAndNombre2(nombreEquipo,clavePartido,enBancaFiltro);

        for (MatchPlayer jp: matchPlayer) {
            ObtenerJugadoresDePartidoyEquipoResponse jpResponse = new ObtenerJugadoresDePartidoyEquipoResponse();

            jpResponse.setJugador(jp.getJugador().getUsuario());
            jpResponse.setFaltas(jp.getFaltas());
            jpResponse.setAsistencias(jp.getAsistencias());
            jpResponse.setEnBanca(jp.getEnBanca());

            jugadoresPartidoResponse.add(jpResponse);

        }


        return jugadoresPartidoResponse;

    }

    public List<String> obtenerJugadoresNoEnPartido(String nombreEquipo, Long clavePartido, String nombreEquipo2) {
        List<String> jugadoresNoEnPartido = new ArrayList<>();
//        jugadoresNoEnPartido = jugadoresEquipoRepository.findJugadoresNoEnPartidos(nombreEquipo, clavePartido, nombreEquipo2);
        return jugadoresNoEnPartido;
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> posicionarJugadorEnPartido(Long clavePartido, String usuario, Boolean enBanca) {
        matchPlayerRepository.posicionarJugadorEnMatch(clavePartido,usuario,enBanca);
        Map<String, Object> jugadorpart = new HashMap<>();
        jugadorpart.put("message", "Jugador actualizado");
        return ResponseEntity.ok(jugadorpart);
    }

    @Transactional
    public SacarJugadorPartidoResponse sacarJugadorDePartido(SacarJugadorDePartidoRequest sacarJugadorDePartidoMensaje) {
        Long clavePartido = sacarJugadorDePartidoMensaje.getClavePartido();
        String jugador = sacarJugadorDePartidoMensaje.getJugador();
        String nombreEquipo = sacarJugadorDePartidoMensaje.getNombreEquipo();
        matchPlayerRepository.setEnBanca(true,clavePartido,jugador,nombreEquipo);

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

        matchPlayerRepository.setEnBanca(false,clavePartido,jugador,nombreEquipo);

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
        return 0;
    }
}
