package com.basket.BasketballSystem.player_tournament;

import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerTournamentRepository extends JpaRepository<PlayerTournament,Long> {
    List<PlayerTournament> findAllByTeamTournament(TeamTournament teamTournament);
    @Transactional
    void deleteByPlayerIdAndTeamTournamentId(Long playerId, Long teamTournamentId);
}
