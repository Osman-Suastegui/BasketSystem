package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/eliminarJugador")
    public ResponseEntity<String> eliminarJugador(@RequestBody Map<String, String> requestMap) {
        String nombreEquipo = requestMap.get("nombreEquipo");
        String nombreJugador = requestMap.get("nombreJugador");

        equipoService.eliminarJugador(nombreEquipo, nombreJugador);

        return ResponseEntity.ok("Jugador eliminado exitosamente.");
    }

    @GetMapping("/buscarEquipoPorNombre")
    public List<Map<String,Object>> buscarEquipoPorNombre(@RequestParam(name = "nombre",required = false) String nombre) {
        return equipoService.buscarEquipoPorNombre(nombre);
    }





}
