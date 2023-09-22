package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PartidoService {
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;
    public List<Map<String,Object>>  obtenerPartidosArbitro(String idArbitro) {
        Usuario arbitro = usuarioRepository.findById(idArbitro).orElse(null);
        if(arbitro == null) throw new BadRequestException("El arbitro no existe");

         List<Partido> partidos =  partidoRepository.findAllByArbitro(arbitro);
         List<Partido> partidosFiltrados = partidos.stream().filter(partido -> partido.getGanador().isEmpty()).
             collect(Collectors.toList());

        partidosFiltrados.sort((partido1, partido2) -> {
            Timestamp fechaInicio1 = partido1.getFechaInicio();
            Timestamp fechaInicio2 = partido2.getFechaInicio();
            return fechaInicio1.compareTo(fechaInicio2);
        });



        List<Map<String,Object>> partidosMap = new ArrayList<>();

        for(Partido partido: partidosFiltrados){
            Map<String,Object> p = new HashMap<>();

            p.put("idPartido",partido.getClavePartido());
            p.put("arbitro",arbitro.getUsuario());
            p.put("fechaInicio",partido.getFechaInicio());
            p.put("temporadaId",partido.getTemporada().getClaveTemporada());
            p.put("equipo1",partido.getEquipo1().getNombre());
            p.put("equipo2",partido.getEquipo2().getNombre());
            partidosMap.add(p);
        }



         return partidosMap;
    }


    public List<Map<String, Object>> obtenerPartidosJugador(String idJugador) {
        Usuario jugador = usuarioRepository.findById(idJugador).orElse(null);
        if (jugador == null) {
            throw new BadRequestException("El jugador no existe");
        }

        List<JugadoresEquipo> jugadoresEquipoTmp = jugadoresEquipoRepository.findAllByJugador(jugador);
        List<Equipo> equipos = new ArrayList<>();
        jugadoresEquipoTmp.forEach(je -> equipos.add(je.getEquipo()));


        // Filtrar partidos relacionados con los equipos del jugador
        List<Partido> partidos = partidoRepository.findAllByEquipo1InOrEquipo2In(equipos,equipos);

        // Filtrar partidos que aún no tienen un ganador (futuros o en curso)
        List<Partido> partidosFiltrados = partidos.stream()
                .filter(partido -> partido.getGanador().isEmpty())
                .collect(Collectors.toList());

        // Ordenar partidos por fecha de inicio
        partidosFiltrados.sort((partido1, partido2) -> {
            Timestamp fechaInicio1 = partido1.getFechaInicio();
            Timestamp fechaInicio2 = partido2.getFechaInicio();
            return fechaInicio1.compareTo(fechaInicio2);
        });

        // Mapear partidos a una lista de Map<String, Object>
        List<Map<String, Object>> partidosMap = new ArrayList<>();

        for (Partido partido : partidosFiltrados) {
            Map<String, Object> p = new HashMap<>();
            p.put("idPartido", partido.getClavePartido());
            p.put("arbitro", partido.getArbitro().getUsuario());
            p.put("fechaInicio", partido.getFechaInicio());
            p.put("temporadaId", partido.getTemporada().getClaveTemporada());
            p.put("equipo1", partido.getEquipo1().getNombre());
            p.put("equipo2", partido.getEquipo2().getNombre());
            partidosMap.add(p);
        }

        return partidosMap;
    }

}
