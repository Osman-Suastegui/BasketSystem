package com.basket.BasketballSystem.estadisticas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartido;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartidoRepository;
import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.partidos.PartidoRepository;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.temporadas.TemporadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EstadisticasJugadorService {

    @Autowired
    JugadorPartidoRepository jugadorPartidoRepository;
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    TemporadaRepository temporadaRepository;
  
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
}
