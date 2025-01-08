package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.tournaments.DTO.TemporadaRequest;
import com.basket.BasketballSystem.tournaments.DTO.TournamentDTO;
import com.basket.BasketballSystem.tournaments.DTO.UserDTO;
import com.basket.BasketballSystem.user_tournament.Role;
import com.basket.BasketballSystem.user_tournament.UserTournament;
import com.basket.BasketballSystem.user_tournament.UserTournamentRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    UserTournamentRepository userTournamentRepository;

    public ResponseEntity<Map<String, Object>> createTournament(Tournament tournament, Long userId) {
        // Default the tournament state if not provided
        if (tournament.getEstado() == null) {
            tournament.setEstado(Estado.INACTIVA);
        }

        // Save the Tournament first
        Tournament savedTournament = tournamentRepository.save(tournament);

        // Get the user
        Optional<Usuario> optionalUser = usuarioRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Usuario user = optionalUser.get();

        // Assign the organizer for the tournament
        UserTournament organizerTournament = new UserTournament();
        organizerTournament.setTournament(savedTournament); // Use the saved tournament
        organizerTournament.setUser(user);
        organizerTournament.setRole(Role.ORGANIZER);

        // Save the UserTournament
        userTournamentRepository.save(organizerTournament);

        // Add the UserTournament to the tournament's collection

        // Return the response
        return ResponseEntity.ok(Map.of(
                "message", "Tournament created successfully",
                "tournament", tournamentRepository.findById(savedTournament.getId())
        ));
    }


    public ResponseEntity<String> modificarDatosTemporada(Long temporadaId, Estado estado) {
        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
        if (tournament == null) return ResponseEntity.badRequest().body("La temporada no existe");
        tournament.setEstado(estado);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Temporada modificada exitosamente.");
    }

//    public ResponseEntity<Map<String, Object>> agregarArbitro(Long temporadaId, String arbitroId) {
//        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
//        Usuario arbitro = usuarioRepository.findById(arbitroId).orElse(null);
//        Map<String, Object> arbitroTemp = new HashMap<>();
//        if (tournament == null) throw new BadRequestException("La temporada no existe");
//        if (arbitro == null) throw new BadRequestException("El arbitro no existe");
//
//        tournamentRepository.save(tournament);
//
//        arbitroTemp.put("message", "Arbitro agregado exitosamente.");
//
//
//        return ResponseEntity.ok(arbitroTemp);
//
//    }

    public List<Usuario> obtenerArbitros(Long temporadaId) {
        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
        if (tournament == null) throw new BadRequestException("La temporada no existe");
        return new ArrayList<>();
    }

    public List<Map<String, Object>> buscarTemporadasPorNombre(String nombreTemporada) {

        List<Tournament> tournaments = tournamentRepository.findByNameContaining(nombreTemporada);

        List<Map<String, Object>> temporadasMap = new ArrayList<>();


        for (Tournament tournament : tournaments) {
            Map<String, Object> t = new HashMap<>();

            t.put("claveTemporada", tournament.getId());
            t.put("nombreTemporada", tournament.getName());
            temporadasMap.add(t);
        }

        return temporadasMap;
    }

    public ResponseEntity<Map<String, Object>> obtenerEstadoTemporada(Long idTemporada) {
        Tournament tournament = tournamentRepository.findById(idTemporada).orElse(null);
        Map<String, Object> arbitroTemp = new HashMap<>();

        arbitroTemp.put("estado", tournament.getEstado().toString());
        return ResponseEntity.ok(arbitroTemp);

    }


    public ResponseEntity<Map<String, Object>> eliminarArbitroDeTemporada(Long temporadaId, String nombreArbitro) {
//        tournamentRepository.deleteArbitroFromTemporada(temporadaId, nombreArbitro);
        Map<String, Object> arbitroTemp = new HashMap<>();
        arbitroTemp.put("message", "Arbitro eliminado exitosamente.");
        return ResponseEntity.ok(arbitroTemp);
    }


    public ResponseEntity<Map<String, Object>> modificarCaracteristicasTemporada(TemporadaRequest request) {
        Long idTemporada = request.getClaveTemporada();
        Tournament tournament = tournamentRepository.findById(idTemporada).orElse(null);
        Map<String, Object> tempNew = new HashMap<>();

        if (tournament == null) throw new BadRequestException("La temporada no existe");
        if (request.getCantidadEquipos() == null) throw new BadRequestException("La cantidad de equipos no puede ser nula");
        if (request.getCantidadPlayoffs() == null) throw new BadRequestException("La cantidad de playoffs no puede ser nula");
        if (request.getCantidadEnfrentamientosRegular() == null)
            throw new BadRequestException("La cantidad de enfrentamientos regulares no puede ser nula");

        tournament.setCantidadEquipos(request.getCantidadEquipos());
        tournamentRepository.save(tournament);

        tempNew.put("message", "Características de la temporada modificada exitosamente.");
        return ResponseEntity.ok(tempNew);
    }

    public ResponseEntity<Map<String, Object>> obtenerCaracteristicasTemporada(Long idTemporada) {
        Tournament tournament = tournamentRepository.findById(idTemporada).orElse(null);
        Map<String, Object> tempNew = new HashMap<>();

        if (tournament == null) throw new BadRequestException("La temporada no existe");

        tempNew.put("cantidadEquipos", tournament.getCantidadEquipos());
        return ResponseEntity.ok(tempNew);
    }

    public ResponseEntity<TournamentDTO> getTournamentById(Long tournamentId) {

        Tournament tournament = tournamentRepository.findTournamentWithUsers(tournamentId);

        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.setId(tournament.getId());
        tournamentDTO.setName(tournament.getName());
        tournamentDTO.setSport(tournament.getSport());
        tournamentDTO.setTournamentType(tournament.getTournamentType());
        tournamentDTO.setRules(tournament.getRules());
        tournamentDTO.setDescription(tournament.getDescription());

        List<UserDTO> users = tournament.getUserTournaments().stream()
                .map(ut -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(ut.getUser().getId());
                    userDTO.setUsername(ut.getUser().getUsername());
                    userDTO.setName(ut.getUser().getName());
                    userDTO.setLastName(ut.getUser().getLastName());
                    userDTO.setRole(ut.getRole().toString());
                    return userDTO;
                }).collect(Collectors.toList());

        tournamentDTO.setUsers(users);

        return ResponseEntity.ok(tournamentDTO);
    }

    public ResponseEntity<List<TournamentDTO>> getTournaments(Long userId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Tournament> tournaments = userTournamentRepository.findDistinctTournamentsByUserId(userId,pageable );
        List<TournamentDTO> tournamentDTOS = tournaments.stream().map(tournament -> {
            TournamentDTO tournamentDTO = new TournamentDTO();
            tournamentDTO.setId(tournament.getId());
            tournamentDTO.setName(tournament.getName());
            tournamentDTO.setSport(tournament.getSport());
            tournamentDTO.setTournamentType(tournament.getTournamentType());
            tournamentDTO.setRules(tournament.getRules());
            tournamentDTO.setDescription(tournament.getDescription());
            return tournamentDTO;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(tournamentDTOS);
    }


}