package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.jugadores_partidos.DTO.ObtenerJugadoresDePartidoyEquipoResponse;
import com.basket.BasketballSystem.jugadores_partidos.DTO.actualizarJugadorPartidoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/JugadorPartido")
public class JugadorPartidoController {

    @Autowired
    JugadorPartidoService jugadorPartidoService;

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @PostMapping("/agregarJugadorPartido")
    public ResponseEntity<Map<String, Object>> agregarJugadorPartido(@RequestBody JugadorPartido jugadorPartido){
       return jugadorPartidoService.agregarJugadorPartido(jugadorPartido);
    }

    @GetMapping("/obtenerJugadoresDePartidoyEquipo")
    public List<ObtenerJugadoresDePartidoyEquipoResponse> obtenerJugadoresDePartidoyEquipo(
            @RequestParam(name = "enBanca", required = false) Boolean enBancaFiltro,
            @RequestParam (name="clavePartido",required = true) Long clavePartido,
            @RequestParam (name="nombreEquipo",required = true) String nombreEquipo) {
        return jugadorPartidoService.obtenerJugadoresDePartidoyEquipo(nombreEquipo,clavePartido,enBancaFiltro);

    }

    @GetMapping("/obtenerJugadoresNoEnPartido")
    public List<String> obtenerJugadoresNoEnPartido(
            @RequestParam (name="clavePartido",required = true) Long clavePartido,
            @RequestParam (name="nombreEquipo",required = true) String nombreEquipo) {
        return jugadorPartidoService.obtenerJugadoresNoEnPartido(nombreEquipo,clavePartido);

    }




}
