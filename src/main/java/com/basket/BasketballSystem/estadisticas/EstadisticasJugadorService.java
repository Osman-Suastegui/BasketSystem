package com.basket.BasketballSystem.estadisticas;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.equipos.EquipoRepository;
import com.basket.BasketballSystem.equipos_temporadas.EquipoTemporadaRepository;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartido;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartidoRepository;
import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.partidos.PartidoRepository;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.temporadas.TemporadaRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    TemporadaRepository temporadaRepository;

    @Autowired
    EquipoRepository equipoRepository;
    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;
  
    public Map<String, Object> jugadorTemporadaEstadisticas(Long idTemporada, String idJugador) {
        Optional<Temporada> temporada = temporadaRepository.findById(idTemporada);
        if(!temporada.isPresent()) new BadRequestException("La temporada no existe");
        List<Partido> partidos = partidoRepository.findAllByTemporada(temporada.get());

        int totalPuntosPorTemporada = 0;
        int tirosDe3puntos = 0;
        int tirosDe2puntos = 0;
        int tirosLibres = 0;
        int asistencias = 0;
        int faltas = 0;

        for (Partido partido : partidos) {
            JugadorPartido jugadorPartido = jugadorPartidoRepository.findByPartidoAndJugador(partido.getClavePartido(), idJugador);
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
        Equipo equipo = equipoRepository.findByNombre(nombreEquipo);


        List<JugadoresEquipo> jugadoresEquipo = jugadoresEquipoRepository.findAllByEquipo(equipo);

        Map<String, Map<String, Object>> estadisticasEquipo = new HashMap<>();

        for (JugadoresEquipo jugadorEquipo : jugadoresEquipo) {

            String idJugador = jugadorEquipo.getJugador().getUsuario();
            Map<String, Object> estadisticasJugador = jugadorTemporadaEstadisticas(temporadaId, idJugador);

            estadisticasEquipo.put(idJugador, estadisticasJugador);
        }

        return estadisticasEquipo;
    }

    public Map<String, Object> topJugadoresTirosLibres(Long temporadaId) {
        Optional<Temporada> temp = temporadaRepository.findById(temporadaId);
        List<Partido> partidos = partidoRepository.findAllByTemporada(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByPartidoIn(partidos);

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

            jugador.put("nombre", jugadorPartido.getJugador().getNombre());
            jugador.put("tirosLibres", jugadorPartido.getTirosLibres());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }

    public Map<String, Object> topJugadoresTirosDe2Puntos(Long temporadaId) {
        Optional<Temporada> temp = temporadaRepository.findById(temporadaId);
        List<Partido> partidos = partidoRepository.findAllByTemporada(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByPartidoIn(partidos);

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

            jugador.put("nombre", jugadorPartido.getJugador().getNombre());
            jugador.put("tirosDe2Puntos", jugadorPartido.getTirosDe2Puntos());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }

    public Map<String, Object> topJugadoresTirosDe3Puntos(Long temporadaId) {
        Optional<Temporada> temp = temporadaRepository.findById(temporadaId);
        List<Partido> partidos = partidoRepository.findAllByTemporada(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByPartidoIn(partidos);

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

            jugador.put("nombre", jugadorPartido.getJugador().getNombre());
            jugador.put("tirosDe3Puntos", jugadorPartido.getTirosDe3Puntos());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }

    public Map<String, Object> topJugadoresAsistencias(Long temporadaId) {
        Optional<Temporada> temp = temporadaRepository.findById(temporadaId);
        List<Partido> partidos = partidoRepository.findAllByTemporada(temp.get());
        List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByPartidoIn(partidos);

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

            jugador.put("nombre", jugadorPartido.getJugador().getNombre());
            jugador.put("asistencias", jugadorPartido.getAsistencias());
            jugador.put("equipo", jugadorPartido.getEquipo());
            jugador.put("posicion", i + 1); // La posición es 1-indexed

            topJugadores.put(idJugador, jugador);
        }

        return topJugadores;
    }


}
