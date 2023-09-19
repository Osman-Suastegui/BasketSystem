package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Equipo")

public class EquipoController {
    EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @RequestMapping("/obtenerEquipoAdminEquipo")
    public Equipo obtenerEquipoAdminEquipo(@RequestParam("idAdminEquipo") String idAdminEquipo) {
        return equipoService.obtenerEquipoAdminEquipo(idAdminEquipo);
    }
    @GetMapping("/{nombreEquipo}/jugadores")
    public List<JugadoresEquipo> obtenerJugadoresPorNombreDelEquipo(@PathVariable String nombreEquipo) {
        return equipoService.obtenerJugadoresPorNombreDelEquipo(nombreEquipo);
    }

    @PostMapping("/crearEquipo")
    public ResponseEntity<String> crearEquipo(@RequestBody Equipo equipo) {

        return equipoService.crearEquipo(equipo);
    }

    @PostMapping("/agregarJugador")
    public ResponseEntity<String> crearJugadoresEquipo( @RequestBody JugadoresEquipo jugadoresEquipo){
        return equipoService.agregarJugador(jugadoresEquipo);
    }



}
