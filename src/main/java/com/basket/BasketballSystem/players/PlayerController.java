package com.basket.BasketballSystem.players;

import com.basket.BasketballSystem.players.DTO.CreatePlayerInTournamentTeamDTO;
import com.basket.BasketballSystem.players.DTO.PlayerDTO;
import com.basket.BasketballSystem.players.DTO.DeletePlayerFromTeamTournamentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @PostMapping("/createPlayerInTournamentTeam")
    public ResponseEntity<PlayerDTO> createPlayerInTournamentTeam(@RequestBody CreatePlayerInTournamentTeamDTO req){
        PlayerDTO response =  playerService.createPlayerInTournamentTeam(req.getTournamentId(),req.getTeamId(),req.getPlayerName());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getPlayersInTournamentTeam")
    public ResponseEntity<List<PlayerDTO>> getPlayersInTournamentTeam(@RequestParam Long tournamentId, @RequestParam Long teamId){

        List<PlayerDTO> res =  playerService.getPlayersInTournamentTeam(tournamentId,teamId);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/deletePlayerFromTeamTournament")
    public ResponseEntity<Void> deletePlayerFromTeamTournament(
            @RequestParam  Long tournamentId,
            @RequestParam  Long teamId,
            @RequestParam  Long playerId) {
        playerService.deletePlayerFromTeamTournament(playerId, teamId, tournamentId);
        return ResponseEntity.ok().build();
    }
}
