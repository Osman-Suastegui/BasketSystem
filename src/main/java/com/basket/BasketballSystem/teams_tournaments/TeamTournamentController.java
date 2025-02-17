package com.basket.BasketballSystem.teams_tournaments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TeamTournament")
public class TeamTournamentController {



    TeamTournamentService teamTournamentService;

    @Autowired
    public TeamTournamentController(TeamTournamentService teamTournamentService) {
        this.teamTournamentService = teamTournamentService;
    }

    @PostMapping("/crearEquipoTemporada")
    public ResponseEntity<Map<String, Object>> crearEquipoTemporada(@RequestBody TeamTournament teamTournament) {
       return teamTournamentService.crearEquipoTemporada(teamTournament);
    }

    @Transactional
    @DeleteMapping("/deleteTeamInTournament")
    public ResponseEntity<Map<String, Object>> eliminarEquipoTemporada(@RequestBody TeamTournament teamTournament) {
       return teamTournamentService.eliminarEquipoTemporada(teamTournament);
    }

    @GetMapping("/obtenerEquiposTemporada")
    public ResponseEntity<Map<String, Object>> obtenerEquiposTemporada(@RequestParam(name = "temporadaId",required = false) Long temporadaId) {
        return teamTournamentService.obtenerEquiposTemporada(temporadaId);
    }


    @GetMapping("/obtenerEquiposNoEnTemporada")
    public List<String> obtenerEquiposNoEnTemporada(@RequestParam(name = "temporadaId",required = false) Long temporadaId) {
        return teamTournamentService.obtenerEquiposNoEnTemporada(temporadaId);
    }





}
