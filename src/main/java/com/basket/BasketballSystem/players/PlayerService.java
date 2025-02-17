package com.basket.BasketballSystem.players;

import com.basket.BasketballSystem.player_tournament.PlayerTournament;
import com.basket.BasketballSystem.player_tournament.PlayerTournamentRepository;
import com.basket.BasketballSystem.players.DTO.PlayerDTO;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import com.basket.BasketballSystem.teams_tournaments.TeamTournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamTournamentRepository teamTournamentRepository;
    @Autowired
    PlayerTournamentRepository playerTournamentRepository;

    @Transactional
    public PlayerDTO createPlayerInTournamentTeam(Long tournamentId, Long teamId,String playerName) {

        TeamTournament teamTournament = teamTournamentRepository.findIdByTournamentIdAndTeamId(tournamentId,teamId);

        Player newPlayer = new Player();
        newPlayer.setName(playerName);
        Player playerSaved = playerRepository.save(newPlayer);
        PlayerTournament playerTournament = new PlayerTournament();
        playerTournament.setTeamTournament(teamTournament);
        playerTournament.setPlayer(newPlayer);
        playerTournamentRepository.save(playerTournament);

//        return value
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(playerTournament.getId());
        playerDTO.setName(playerSaved.getName());
        return playerDTO;
    }

    public List<PlayerDTO> getPlayersInTournamentTeam(Long tournamentId, Long teamId) {
        TeamTournament teamTournament = teamTournamentRepository.findIdByTournamentIdAndTeamId(tournamentId,teamId);
        List<PlayerTournament> playerTournaments = playerTournamentRepository.findAllByTeamTournament(teamTournament);

        List<PlayerDTO> playersResponse = new ArrayList<>();

        for (PlayerTournament player: playerTournaments){
            PlayerDTO playerResponseDTO = new PlayerDTO();
            playerResponseDTO.setName(player.getPlayer().getName());
            playerResponseDTO.setId(player.getPlayer().getId());
            playersResponse.add(playerResponseDTO);
        }

        return playersResponse;
    }

    public void deletePlayerFromTeamTournament(Long playerId, Long teamId, Long tournamentId) {
        TeamTournament teamTournament = teamTournamentRepository.findIdByTournamentIdAndTeamId(tournamentId,teamId);
        playerTournamentRepository.deleteByPlayerIdAndTeamTournamentId(playerId,teamTournament.getId());
    }

//    params playerId,team,tournamentId
}
