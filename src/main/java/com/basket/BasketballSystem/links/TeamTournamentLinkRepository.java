package com.basket.BasketballSystem.links;

import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import io.swagger.v3.oas.models.links.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamTournamentLinkRepository extends JpaRepository<TeamTournamentLink,Long> {

    List<TeamTournamentLink> findByTeamTournamentOrderByCreatedAtDesc(TeamTournament teamTournament);

    @Query("SELECT ttl FROM TeamTournamentLink ttl WHERE ttl.teamTournament.team.id = :teamId AND ttl.teamTournament.tournament.id = :tournamentId")
    List<TeamTournamentLink> findLinksByTeamAndTournament(@Param("teamId") Long teamId, @Param("tournamentId") Long tournamentId);
}
