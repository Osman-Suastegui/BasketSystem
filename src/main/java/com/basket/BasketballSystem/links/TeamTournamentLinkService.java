package com.basket.BasketballSystem.links;

import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import com.basket.BasketballSystem.teams_tournaments.TeamTournamentRepository;
import com.basket.BasketballSystem.teams_tournaments.TeamTournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.SysexMessage;
import java.util.List;
import java.util.UUID;

@Service
public class TeamTournamentLinkService {
    @Autowired
    private TeamTournamentLinkRepository linkRepository;
    @Autowired
    private TeamTournamentRepository teamTournamentRepository;

    public TeamTournamentLink generateInvite(Long teamId, Long tournamentId) {

        TeamTournament teamTournament = teamTournamentRepository.findIdByTournamentIdAndTeamId(tournamentId,teamId);
        TeamTournamentLink link = new TeamTournamentLink();
        link.setTeamTournament(teamTournament);
        link.setToken(UUID.randomUUID().toString());
        linkRepository.save(link);
        return  link;
    }

    public TeamTournamentLink generateInvite(TeamTournament teamTournament){
        TeamTournamentLink link = new TeamTournamentLink();
        link.setTeamTournament(teamTournament);
        link.setToken(UUID.randomUUID().toString());
        linkRepository.save(link);
        return  link;
    }

    public TeamTournamentLink getLink(Long teamId, Long tournamentId) {
        TeamTournament teamTournament = teamTournamentRepository.findIdByTournamentIdAndTeamId(tournamentId,teamId);
        List<TeamTournamentLink> links = linkRepository.findByTeamTournamentOrderByCreatedAtDesc(teamTournament);

        TeamTournamentLink teamTournamentLinkResponse = null;
//        IF THERE ARE NO LINKS WE GENERATE ONE, OTHERWISE WE USE THE MOST RECENT ONE
        if(links.isEmpty()){
            teamTournamentLinkResponse = generateInvite(teamTournament);
        }else{
            teamTournamentLinkResponse = links.get(0);
        }
        return teamTournamentLinkResponse;
    }


    public boolean validateLink(Long teamId, Long tournamentId, String token) {
        List<TeamTournamentLink> links =  linkRepository.findLinksByTeamAndTournament(teamId, tournamentId);

        if (links == null || links.isEmpty()) {
            return false;
        }
        // Loop through the links list and compare the token
        for (TeamTournamentLink link : links) {
            if (link.getToken().equals(token)) {
                return true;  // Token matches, return true
            }
        }

        return false;
    }
}
