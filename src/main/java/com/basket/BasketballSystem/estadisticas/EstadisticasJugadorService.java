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
    public int cantidadPuntosPorTemporada(Long idTemporada,String idJugador){
        Optional<Temporada> temporada = temporadaRepository.findById(idTemporada);
        if(!temporada.isPresent()) new BadRequestException("La temporada no existe");
        List<Partido> partidos = partidoRepository.findAllByTemporada(temporada.get());

        int totalPuntos = 0;

        for (Partido partido : partidos) {
            JugadorPartido jugadorPartido = jugadorPartidoRepository.findByPartidoAndJugador(partido.getClavePartido(), idJugador);
            if(jugadorPartido == null)continue;
            totalPuntos += jugadorPartido.getTirosDe2Puntos() * 2 + jugadorPartido.getTirosDe3Puntos() * 3 + jugadorPartido.getTirosLibres();

        }
        return totalPuntos;
    }

    public Map<String, Object> jugadorTemporadaEstadisticas(Long temporada, String idJugador) {
        int totalPuntos =  cantidadPuntosPorTemporada(temporada,idJugador);
        Map<String,Object> estadisticas = new HashMap<>();
        estadisticas.put("totalPuntosPorTemporada",totalPuntos);
        return estadisticas;
    }
}
