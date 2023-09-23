package com.basket.BasketballSystem.partidos;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Partido")
public class PartidoController {

    @Autowired
    PartidoService partidoService;

    @RequestMapping ("/obtenerPartidosArbitro")
    public List<Map<String,Object>>  obtenerPartidosArbitro(@RequestParam("idArbitro") String idArbitro){

        return partidoService.obtenerPartidosArbitro(idArbitro);
    }

    @RequestMapping ("/obtenerPartidosJugador")
    public List<Map<String,Object>>  obtenerPartidosJugador(@RequestParam("idJugador") String idJugador){

        return partidoService.obtenerPartidosJugador(idJugador);
    }

//    obtenerPartidosTemporada
    @RequestMapping ("/obtenerPartidosTemporada")
    public List<Map<String,Object>>  obtenerPartidosTemporada(@RequestParam("idTemporada") Long idTemporada){

        return partidoService.obtenerPartidosTemporada(idTemporada);
    }

    @PutMapping("/agendar")
    public ResponseEntity<String> agendarPartido(@RequestBody Partido partido){
        return partidoService.agendarPartido(partido);
    }




}
