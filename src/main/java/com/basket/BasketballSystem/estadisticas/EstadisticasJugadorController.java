package com.basket.BasketballSystem.estadisticas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasJugadorController {

    @Autowired
    EstadisticasJugadorService estadisticasJugadorService;


    @RequestMapping("/jugador-temporada")
    public Map<String, Object> jugadorTemporadaEstadisticas
        (@RequestParam(name = "idJugador") String idJugador,
        @RequestParam(name = "idTemporada") Long temporada) {
        return estadisticasJugadorService.jugadorTemporadaEstadisticas(temporada,idJugador);
    }
    @RequestMapping("/jugador-general")
    public Map<String,Object> jugadorGeneralEstadisticas(@RequestParam(name="idJugador") String idJugador){
        return estadisticasJugadorService.jugadorGeneralEstadisticas(idJugador);
    }

    @RequestMapping("/equipo-temporada-estadisticas")
    public Map<String, Map<String, Object>> equipoTemporadaEstadisticas(@RequestParam(name="nombreEquipo") String nombreEquipo,
                                                          @RequestParam(name="temporadaId") Long temporadaId){
        return estadisticasJugadorService.equipoTemporadaEstadisticas(nombreEquipo,temporadaId);
    }

    @RequestMapping("/top-jugadores-tiros-libres")
    public Map<String, Object> topJugadoresTirosLibres(@RequestParam(name="temporadaId") Long temporadaId){
        return estadisticasJugadorService.topJugadoresTirosLibres(temporadaId);
    }

    @RequestMapping("/top-jugadores-tiros-de-2-puntos")
    public Map<String, Object> topJugadoresTirosDe2Puntos(@RequestParam(name="temporadaId") Long temporadaId){
        return estadisticasJugadorService.topJugadoresTirosDe2Puntos(temporadaId);
    }

    @RequestMapping("/top-jugadores-tiros-de-3-puntos")
    public Map<String, Object> topJugadoresTirosDe3Puntos(@RequestParam(name="temporadaId") Long temporadaId){
        return estadisticasJugadorService.topJugadoresTirosDe3Puntos(temporadaId);
    }

    @RequestMapping("/top-jugadores-asistencias")
    public Map<String, Object> topJugadoresAsistencias(@RequestParam(name="temporadaId") Long temporadaId){
        return estadisticasJugadorService.topJugadoresAsistencias(temporadaId);
    }



}
