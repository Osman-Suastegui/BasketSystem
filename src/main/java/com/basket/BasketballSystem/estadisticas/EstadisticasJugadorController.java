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

}
