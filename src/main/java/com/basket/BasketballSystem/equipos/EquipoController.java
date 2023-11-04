package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @RequestMapping("/obtenerEquipoAdminEquipo")
    public Equipo obtenerEquipoAdminEquipo(@RequestParam("idAdminEquipo") String idAdminEquipo) {
        return equipoService.obtenerEquipoAdminEquipo(idAdminEquipo);
    }
    @GetMapping("/{nombreEquipo}/jugadores")
    public List<JugadoresEquipo> obtenerJugadoresPorNombreDelEquipo(@PathVariable String nombreEquipo) {
        return equipoService.obtenerJugadoresPorNombreDelEquipo(nombreEquipo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @PostMapping("/crearEquipo")
    public ResponseEntity<Map<String, Object>> crearEquipo(@RequestBody Equipo equipo) {

        return equipoService.crearEquipo(equipo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @PostMapping("/agregarJugador")
    public ResponseEntity<Map<String, Object>> crearJugadoresEquipo( @RequestBody JugadoresEquipo jugadoresEquipo){
        return equipoService.agregarJugador(jugadoresEquipo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @DeleteMapping("/eliminarJugador")
    public ResponseEntity<Map<String, Object>> eliminarJugador(@RequestBody Map<String, String> requestMap) {
        String nombreEquipo = requestMap.get("nombreEquipo");
        String nombreJugador = requestMap.get("nombreJugador");

        return equipoService.eliminarJugador(nombreEquipo, nombreJugador);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @GetMapping("/obtenerJugadoresParaEquipo")
    public List<Usuario> obtenerJugadoresParaEquipo(@RequestParam(name = "nombreEquipo",required = false) String nombreEquipo) {
        return equipoService.obtenerJugadoresParaEquipo(nombreEquipo);
    }


    @GetMapping("/buscarEquipoPorNombre")
    public List<Map<String,Object>> buscarEquipoPorNombre(@RequestParam(name = "nombre",required = false) String nombre) {
        return equipoService.buscarEquipoPorNombre(nombre);
    }





}
