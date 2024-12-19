package com.basket.BasketballSystem.teams;


import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.teams_players.DTO.JugadoresEquipoDTO;
import com.basket.BasketballSystem.teams_players.TeamPlayer;
import com.basket.BasketballSystem.teams_players.TeamPlayerRepository;

import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Autowired
    TeamPlayerRepository teamPlayerRepository;


    public Team obtenerEquipoAdminEquipo(String idAdminEquipo){
        //System.out.println("idAdminEquipo: " + idAdminEquipo);
        if(teamRepository.findByidAdminEquipo(idAdminEquipo).orElse(null) == null){
            throw new BadRequestException("No tiene un equipo asignado");
        }
        return teamRepository.findByidAdminEquipo(idAdminEquipo).orElse(null);

    }

    public List<TeamPlayer> obtenerJugadoresPorNombreDelEquipo(String nombreEquipo) {
        if(nombreEquipo == null){
          throw new BadRequestException("El nombre del equipo no puede ser nulo");
        }
        Optional<Team> equipo = teamRepository.findById(nombreEquipo);
        if(equipo.isPresent()){
            return equipo.get().getJugadores();
        }

        return null;
    }


    public ResponseEntity<Map<String, Object>> crearEquipo(Team equipo) {

        if (equipo.getName() == null || equipo.getName().isEmpty()) {
            throw new BadRequestException("El nombre del equipo no puede ser nulo o vacío");
        }

        String adminEquipo = equipo.getUsuario_Admin_equipo();
        if (adminEquipo == null) {
            throw new BadRequestException("El usuario administrador del equipo no puede ser nulo");
        }

        if (teamRepository.findById(equipo.getName()).isPresent()) {
            throw new BadRequestException("El equipo ya existe");
        }


        teamRepository.save(equipo);
        Map<String, Object> team = new HashMap<>();

        team.put("message", "Equipo Creado Exitosamente.");

        return ResponseEntity.ok(team);
    }


    public ResponseEntity<Map<String, Object>> agregarJugador(JugadoresEquipoDTO jugadoresEquipoDTO) {
        // Crear una instancia de JugadoresEquipo
        TeamPlayer teamplayers = new TeamPlayer();
        //valida que la posicion no sea nula
        if(jugadoresEquipoDTO.getPosicion() == null){
            throw new BadRequestException("La posicion no puede ser nula");
        }
        teamplayers.setPosition(jugadoresEquipoDTO.getPosicion());

        // Buscar y configurar el equipo
        String equipoNombre = jugadoresEquipoDTO.getEquipoNombre();
        Team team = teamRepository.findByName(equipoNombre);

        if (team == null) {
            throw new BadRequestException("El equipo no existe");
        }

        // Buscar y configurar el jugador
        String jugadorUsuario = jugadoresEquipoDTO.getJugadorUsuario();

        Usuario jugador = usuarioRepository.findByUsuario(jugadorUsuario).orElse(null);
        if (jugador == null) {
            throw new BadRequestException("El usuario del jugador no existe");
        }

        if (jugador == null) {
            throw new BadRequestException("El usuario del jugador no existe");
        }

        teamplayers.setEquipo(team);
        teamplayers.setPlayer(jugador);

        // Verificar si el jugador ya existe en el equipo
        for (TeamPlayer jugadorEnEquipo : team.getJugadores()) {
            if (jugadorEnEquipo.getPlayer().getUsuario().equals(jugadorUsuario)) {
                throw new BadRequestException("El jugador ya existe en el equipo");
            }
        }

        // Guardar la instancia de JugadoresEquipo en el repositorio
        team.addJugador(teamplayers);
        teamRepository.save(team);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Jugador asignado correctamente");

        return ResponseEntity.ok(response);
    }
    @Transactional
    public ResponseEntity<Map<String, Object>> eliminarJugador(String nombreEquipo, String nombreJugador) {
        teamPlayerRepository.deleteByPlayerAndTeam(nombreEquipo, nombreJugador);
        Map<String, Object> team = new HashMap<>();

        team.put("message", "Jugador eliminado exitosamente.");
        return ResponseEntity.ok(team);
    }

    public List<Map<String,Object>> buscarEquipoPorNombre(String nombre) {

        List<Team> teams = teamRepository.findByNameContaining(nombre);

        List<Map<String,Object>> equiposMap = new ArrayList<>();

        for(Team team : teams){
            Map<String,Object> e = new HashMap<>();
            e.put("nombre", team.getName());
            equiposMap.add(e);
        }
        return equiposMap;
    }


    public List<Usuario> obtenerJugadoresParaEquipo(String nombreEquipo) {
        Team team = teamRepository.findByName(nombreEquipo);

        List<Usuario> jugadores = teamPlayerRepository.findPlayersNotInTeamWithAgeAndGenderCondition(nombreEquipo);
            return jugadores;
    }







}
