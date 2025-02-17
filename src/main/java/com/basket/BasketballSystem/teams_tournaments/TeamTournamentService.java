package com.basket.BasketballSystem.teams_tournaments;

import com.basket.BasketballSystem.links.TeamTournamentLink;
import com.basket.BasketballSystem.links.TeamTournamentLinkRepository;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.tournaments.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamTournamentService {

    @Autowired
    TournamentRepository TemporadaRepository;
    @Autowired
    TeamTournamentLinkRepository teamTournamentLinkRepository;

    @Autowired
    TeamTournamentRepository teamTournamentRepository;

    @Autowired
    public TeamTournamentService(TeamTournamentRepository teamTournamentRepository) {
        this.teamTournamentRepository = teamTournamentRepository;
    }


    public ResponseEntity<Map<String, Object>> crearEquipoTemporada(TeamTournament teamTournament) {

        if(teamTournament.getTemporada() == null ){
            throw new BadRequestException("La temporada no puede ser nula o vacia");
        }
        if(teamTournament.getEquipo().getName() == null ){
            throw new BadRequestException("El equipo no puede ser nulo o vacio");
        }

        Map<String, Object> equipoTemp = new HashMap<>();
        teamTournamentRepository.save(teamTournament);
        equipoTemp.put("message", "Arbitro agregado exitosamente.");


        return ResponseEntity.ok(equipoTemp);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> eliminarEquipoTemporada(TeamTournament teamTournamentReq) {
        Long tournamentId = teamTournamentReq.getTemporada().getId();
        Long teamId = teamTournamentReq.getEquipo().getId();

        TeamTournament teamTournament = teamTournamentRepository.findIdByTournamentIdAndTeamId(tournamentId,teamId);
        teamTournamentLinkRepository.deleteByTeamTournamentId(teamTournament.getId());
        teamTournamentRepository.deleteById(teamTournament.getId());
//
//        teamTournamentRepository.delete(teamTournament);
        Map<String, Object> EquipoMap = new HashMap<>();
        EquipoMap.put("message", "Equipo eliminado exitosamente.");
            return ResponseEntity.ok(EquipoMap);
    }



    public ResponseEntity<Map<String, Object>> obtenerEquiposTemporada(Long temporadaId) {

        if (temporadaId == null) {
            throw new BadRequestException("La temporada no existe");
        }

        List<Team> teams = teamTournamentRepository.findTeamsByTournamentId(temporadaId);



        Map<String, Object> nombreEquipoMap = new HashMap<>();
        for (Team team : teams) {
            nombreEquipoMap.put(team.getName(), team.getName()); // Utiliza el nombre del equipo como clave
        }

        return ResponseEntity.ok(nombreEquipoMap);
    }



    public List<String> obtenerEquiposNoEnTemporada(Long temporadaId) {
        Optional<Tournament> optionalTemporada = TemporadaRepository.findById(temporadaId);
        if(optionalTemporada.isEmpty()) return new ArrayList<>();
        Tournament temp = optionalTemporada.get();

        List<String> equipos = teamTournamentRepository.findTeamsNotInTemporadaAndCategoryAndGender(temporadaId);


        return equipos;
    }


}
