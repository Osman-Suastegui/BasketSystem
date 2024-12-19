package com.basket.BasketballSystem.matches_player;

import com.basket.BasketballSystem.matches_player.DTO.ObtenerJugadoresDePartidoyEquipoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/JugadorPartido")
public class MatchPlayerController {

    @Autowired
    MatchPlayerService matchPlayerService;

    @PostMapping("/agregarJugadorPartido")
    public ResponseEntity<Map<String, Object>> agregarJugadorPartido(@RequestBody MatchPlayer matchPlayer){
       return matchPlayerService.agregarJugadorPartido(matchPlayer);
    }

    @GetMapping("/obtenerJugadoresDePartidoyEquipo")
    public List<ObtenerJugadoresDePartidoyEquipoResponse> obtenerJugadoresDePartidoyEquipo(
            @RequestParam(name = "enBanca", required = false) Boolean enBancaFiltro,
            @RequestParam (name="clavePartido",required = true) Long clavePartido,
            @RequestParam (name="nombreEquipo",required = true) String nombreEquipo) {
        return matchPlayerService.obtenerJugadoresDePartidoyEquipo(nombreEquipo,clavePartido,enBancaFiltro);

    }

    @GetMapping("/obtenerJugadoresNoEnPartido")
    public List<String> obtenerJugadoresNoEnPartido(
            @RequestParam (name="clavePartido",required = true) Long clavePartido,
            @RequestParam (name="nombreEquipo",required = true) String nombreEquipo,
            @RequestParam (name="otroEquipo",required = true) String otroEquipo) {
        return matchPlayerService.obtenerJugadoresNoEnPartido(nombreEquipo,clavePartido, otroEquipo);

    }

    @PutMapping("/PosicionarJugador")
    public ResponseEntity<Map<String, Object>> posicionarJugadorEnPartido(
            @RequestParam (name="clavePartido",required = true) Long clavePartido,
            @RequestParam (name="usuario",required = true) String usuario,

            @RequestParam (name="enBanca",required = true) Boolean enBanca) {
        return matchPlayerService.posicionarJugadorEnPartido(clavePartido,usuario,enBanca);
    }

    @GetMapping("/obtenerPuntosEquipo")
    public ResponseEntity<Map<String, Object>> obtenerPuntosEquipo(@RequestParam (name="clavePartido",required = true) Long clavePartido,
                                                                   @RequestParam (name="nombreEquipo",required = true) String nombreEquipo) {
        int puntos = matchPlayerService.sumarPuntosPorEquipoYPartido(nombreEquipo,clavePartido);
        Map<String, Object> puntosEquipo = Map.of("puntos", puntos);
        return ResponseEntity.ok(puntosEquipo);
    }






}
