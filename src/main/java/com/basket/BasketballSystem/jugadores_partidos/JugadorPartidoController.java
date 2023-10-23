package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.jugadores_partidos.DTO.ObtenerJugadoresDePartidoyEquipoResponse;
import com.basket.BasketballSystem.jugadores_partidos.DTO.actualizarJugadorPartidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/JugadorPartido")
public class JugadorPartidoController {

    @Autowired
    JugadorPartidoService jugadorPartidoService;

    @PostMapping("/agregarJugadorPartido")
    public ResponseEntity<String> agregarJugadorPartido(@RequestBody JugadorPartido jugadorPartido){
       return jugadorPartidoService.agregarJugadorPartido(jugadorPartido);
    }

    @PutMapping("/actualizarJugadorPartido")
    public ResponseEntity<String> actualizarJugadorPartido(@RequestBody actualizarJugadorPartidoDTO jugadorPartidoDTO){


        return jugadorPartidoService.actualizarJugadorPartido(jugadorPartidoDTO);
    }

    @GetMapping("/obtenerJugadoresDePartidoyEquipo")
    public List<ObtenerJugadoresDePartidoyEquipoResponse> obtenerJugadoresDePartidoyEquipo(
            @RequestParam(name = "enBanca", required = false) Boolean enBancaFiltro,
            @RequestParam (name="clavePartido",required = true) Long clavePartido,
            @RequestParam (name="nombreEquipo",required = true) String nombreEquipo) {

        return jugadorPartidoService.obtenerJugadoresDePartidoyEquipo(nombreEquipo,clavePartido,enBancaFiltro);

    }




}
