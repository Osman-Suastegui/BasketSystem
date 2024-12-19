package com.basket.BasketballSystem.teams;

import com.basket.BasketballSystem.teams_players.DTO.JugadoresEquipoDTO;
import com.basket.BasketballSystem.teams_players.TeamPlayer;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Equipo")

public class TeamController {
    TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @RequestMapping("/obtenerEquipoAdminEquipo")
    public Team obtenerEquipoAdminEquipo(@RequestParam("idAdminEquipo") String idAdminEquipo) {
        return teamService.obtenerEquipoAdminEquipo(idAdminEquipo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @GetMapping("/{nombreEquipo}/jugadores")
    public List<TeamPlayer> obtenerJugadoresPorNombreDelEquipo(@PathVariable String nombreEquipo) {
        return teamService.obtenerJugadoresPorNombreDelEquipo(nombreEquipo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @PostMapping("/crearEquipo")
    public ResponseEntity<Map<String, Object>> crearEquipo(@RequestBody Team team) {

        return teamService.crearEquipo(team);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @PostMapping("/agregarJugador")
    public ResponseEntity<Map<String, Object>> crearJugadoresEquipo(@RequestBody JugadoresEquipoDTO jugadoresEquipoDTO) {
        return teamService.agregarJugador(jugadoresEquipoDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @DeleteMapping("/eliminarJugador")
    public ResponseEntity<Map<String, Object>> eliminarJugador(
            @RequestParam String nombreEquipo,
            @RequestParam String nombreJugador
    ) {
        return teamService.eliminarJugador(nombreEquipo, nombreJugador);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @GetMapping("/obtenerJugadoresParaEquipo")
    public List<Usuario> obtenerJugadoresParaEquipo(@RequestParam(name = "nombreEquipo",required = false) String nombreEquipo) {
        return teamService.obtenerJugadoresParaEquipo(nombreEquipo);
    }


    @GetMapping("/buscarEquipoPorNombre")
    public List<Map<String,Object>> buscarEquipoPorNombre(@RequestParam(name = "nombre",required = false) String nombre) {
        return teamService.buscarEquipoPorNombre(nombre);
    }





}
