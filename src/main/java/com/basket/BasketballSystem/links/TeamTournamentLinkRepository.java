package com.basket.BasketballSystem.links;

import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamTournamentLinkRepository extends JpaRepository<TeamTournamentLink,Long> {

    List<TeamTournamentLink> findByTeamTournamentOrderByCreatedAtDesc(TeamTournament teamTournament);
}
