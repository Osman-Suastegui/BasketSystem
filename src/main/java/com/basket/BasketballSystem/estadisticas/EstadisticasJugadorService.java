package com.basket.BasketballSystem.estadisticas;

import com.basket.BasketballSystem.matches.Match;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.teams.TeamRepository;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.TeamPlayer;
import com.basket.BasketballSystem.jugadores_equipos.TeamPlayerRepository;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartido;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartidoRepository;
import com.basket.BasketballSystem.matches.PartidoRepository;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.tournaments.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstadisticasJugadorService {

    @Autowired
    JugadorPartidoRepository jugadorPartidoRepository;
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamPlayerRepository teamPlayerRepository;
  
    public Map<String, Object> jugadorTemporadaEstadisticas(Long idTemporada, String idJugador) {
        Optional<Tournament> temporada = tournamentRepository.findById(idTemporada);
        if(!temporada.isPresent()) new BadRequestException("La temporada no existe");
        List<Match> matches = partidoRepository.findAllByTournament(temporada.get());

        int totalPuntosPorTemporada = 0;
        int tirosDe3puntos = 0;
        int tirosDe2puntos = 0;
        int tirosLibres = 0;
        int asistencias = 0;
        int faltas = 0;

        for (Match match : matches) {
            JugadorPartido jugadorPartido = jugadorPartidoRepository.findByMatchAndJugador(match.getClavePartido(), idJugador);
            if(jugadorPartido == null)continue;
            tirosDe3puntos += jugadorPartido.getTirosDe3Puntos();
            tirosDe2puntos += jugadorPartido.getTirosDe2Puntos();
            tirosLibres += jugadorPartido.getTirosLibres();
            asistencias += jugadorPartido.getAsistencias();
            faltas += jugadorPartido.getFaltas();
        }
        totalPuntosPorTemporada = tirosDe3puntos*3 + tirosDe2puntos*2 + tirosLibres;
        Map<String,Object> estadisticas = new HashMap<>();
        estadisticas.put("totalPuntosPorTemporada",totalPuntosPorTemporada);
        estadisticas.put("tirosDe3puntosPorTemporada",tirosDe3puntos);
        estadisticas.put("tirosDe2puntosPorTemporada",tirosDe2puntos);
        estadisticas.put("tirosLibresPorTemporada",tirosLibres);
        estadisticas.put("asistenciasPorTemporada",asistencias);
        estadisticas.put("faltasPorTemporada",faltas);
        return estadisticas;
    }

    public Map<String, Object> jugadorGeneralEstadisticas(String idJugador) {
        List<JugadorPartido> juegosJugador = jugadorPartidoRepository.findByJugador(idJugador);
        int totalPuntosPorTemporada = 0;
        int tirosDe3puntos = 0;
        int tirosDe2puntos = 0;
        int tirosLibres = 0;
        int asistencias = 0;
        int faltas = 0;

        for(JugadorPartido j: juegosJugador){
            tirosDe3puntos += j.getTirosDe3Puntos();
            tirosDe2puntos += j.getTirosDe2Puntos();
            tirosLibres += j.getTirosLibres();
            asistencias += j.getAsistencias();
            faltas += j.getFaltas();
        }
        totalPuntosPorTemporada = tirosDe3puntos * 3 + tirosDe2puntos * 2 + tirosLibres;

        Map<String,Object> estadisticas = new HashMap<>();
        estadisticas.put("totalPuntosGenerales",totalPuntosPorTemporada);
        estadisticas.put("tirosDe3puntosGenerales",tirosDe3puntos);
        estadisticas.put("tirosDe2puntosGenerales",tirosDe2puntos);
        estadisticas.put("tirosLibresGenerales",tirosLibres);
        estadisticas.put("asistenciasGenerales",asistencias);
        estadisticas.put("faltasGenerales",faltas);
        return estadisticas;
    }

    public Map<String, Map<String, Object>> equipoTemporadaEstadisticas(String nombreEquipo, Long temporadaId) {
        Team team = teamRepository.findByName(nombreEquipo);


        List<TeamPlayer> teamplayers = teamPlayerRepository.findAllByTeam(team);

        Map<String, Map<String, Object>> estadisticasEquipo = new HashMap<>();

        for (TeamPlayer jugadorEquipo : teamplayers) {

            String idJugador = jugadorEquipo.getPlayer().getUsuario();
            Map<String, Object> estadisticasJugador = jugadorTemporadaEstadisticas(temporadaId, idJugador);

            estadisticasEquipo.put(idJugador, estadisticasJugador);
        }

        return estadisticasEquipo;
    }

    public Map<String, Object> topJugadoresTirosLibres(Long temporadaId) {
        Optional<Tournament> temp = tournamentRepository.findById(temporadaId);
        List<Match> matches = partidoRepository.findAllByTournament(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByMatchIn(matches);

        // Ordena la lista de jugadores por la cantidad de tiros libres en orden descendente
        List<JugadorPartido> jugadoresOrdenados = jugadoresPartido.stream()
                .sorted(Comparator.comparingInt(JugadorPartido::getTirosLibres).reversed())
                .collect(Collectors.toList());

        Map<String, Object> topJugadores = new HashMap<>();

        // Selecciona los primeros 5 jugadores (o menos si hay menos de 5)
        int cantidadJugadores = Math.min(jugadoresOrdenados.size(), 5);
        for (int i = 0; i < cantidadJugadores; i++) {
            JugadorPartido jugadorPartido = jugadoresOrdenados.get(i);

            String idJugador = jugadorPartido.getJugador().getUsuario();
            Map<String, Object> jugador = new HashMap<>();

            jugador.put("nombre", jugadorPartido.getJugador().getName());
            jugador.put("tirosLibres", jugadorPartido.getTirosLibres());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }

    public Map<String, Object> topJugadoresTirosDe2Puntos(Long temporadaId) {
        Optional<Tournament> temp = tournamentRepository.findById(temporadaId);
        List<Match> matches = partidoRepository.findAllByTournament(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByMatchIn(matches);

        // Ordena la lista de jugadores por la cantidad de tiros de 2 puntos en orden descendente
        List<JugadorPartido> jugadoresOrdenados = jugadoresPartido.stream()
                .sorted(Comparator.comparingInt(JugadorPartido::getTirosDe2Puntos).reversed())
                .collect(Collectors.toList());

        Map<String, Object> topJugadores = new HashMap<>();

        // Selecciona los primeros 5 jugadores (o menos si hay menos de 5)
        int cantidadJugadores = Math.min(jugadoresOrdenados.size(), 5);
        for (int i = 0; i < cantidadJugadores; i++) {
            JugadorPartido jugadorPartido = jugadoresOrdenados.get(i);

            String idJugador = jugadorPartido.getJugador().getUsuario();
            Map<String, Object> jugador = new HashMap<>();

            jugador.put("nombre", jugadorPartido.getJugador().getName());
            jugador.put("tirosDe2Puntos", jugadorPartido.getTirosDe2Puntos());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }

    public Map<String, Object> topJugadoresTirosDe3Puntos(Long temporadaId) {
        Optional<Tournament> temp = tournamentRepository.findById(temporadaId);
        List<Match> matches = partidoRepository.findAllByTournament(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByMatchIn(matches);

        // Ordena la lista de jugadores por la cantidad de tiros de 3 puntos en orden descendente
        List<JugadorPartido> jugadoresOrdenados = jugadoresPartido.stream()
                .sorted(Comparator.comparingInt(JugadorPartido::getTirosDe3Puntos).reversed())
                .collect(Collectors.toList());

        Map<String, Object> topJugadores = new HashMap<>();

        // Selecciona los primeros 5 jugadores (o menos si hay menos de 5)
        int cantidadJugadores = Math.min(jugadoresOrdenados.size(), 5);
        for (int i = 0; i < cantidadJugadores; i++) {
            JugadorPartido jugadorPartido = jugadoresOrdenados.get(i);

            String idJugador = jugadorPartido.getJugador().getUsuario();
            Map<String, Object> jugador = new HashMap<>();

            jugador.put("nombre", jugadorPartido.getJugador().getName());
            jugador.put("tirosDe3Puntos", jugadorPartido.getTirosDe3Puntos());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }

    public Map<String, Object> topJugadoresAsistencias(Long temporadaId) {
        Optional<Tournament> temp = tournamentRepository.findById(temporadaId);
        List<Match> matches = partidoRepository.findAllByTournament(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByMatchIn(matches);

        // Ordena la lista de jugadores por la cantidad de asistencias en orden descendente
        List<JugadorPartido> jugadoresOrdenados = jugadoresPartido.stream()
                .sorted(Comparator.comparingInt(JugadorPartido::getAsistencias).reversed())
                .collect(Collectors.toList());

        Map<String, Object> topJugadores = new HashMap<>();

        // Selecciona los primeros 5 jugadores (o menos si hay menos de 5)
        int cantidadJugadores = Math.min(jugadoresOrdenados.size(), 5);
        for (int i = 0; i < cantidadJugadores; i++) {
            JugadorPartido jugadorPartido = jugadoresOrdenados.get(i);

            String idJugador = jugadorPartido.getJugador().getUsuario();
            Map<String, Object> jugador = new HashMap<>();

            jugador.put("nombre", jugadorPartido.getJugador().getName());
            jugador.put("asistencias", jugadorPartido.getAsistencias());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }


}
