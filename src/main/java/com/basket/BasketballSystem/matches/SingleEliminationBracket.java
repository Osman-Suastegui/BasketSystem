package com.basket.BasketballSystem.matches;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.teams.TeamRepository;
import com.basket.BasketballSystem.teams_tournaments.TeamTournamentRepository;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.tournaments.TournamentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingleEliminationBracket implements MatchGenerator {

    private final PartidoRepository partidoRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamTournamentRepository teamTournamentRepository;
    public SingleEliminationBracket(PartidoRepository partidoRepository, TournamentRepository tournamentRepository, TeamTournamentRepository teamTournamentRepository) {
        this.partidoRepository = partidoRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamTournamentRepository = teamTournamentRepository;
    }

    @Override
    public List<Match> generateBracket(Long tournamentId) {
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        if(tournament.isEmpty()) throw  new BadRequestException("Tournament not found");
        boolean exists = partidoRepository.existsByTournamentId(tournamentId);
        if(exists){
            throw  new BadRequestException("Matches already exist in the tournament");
        }

        List<Team> teams = teamTournamentRepository.findTeamsByTournamentId(tournamentId);
        if(!canGenerateBracket(teams.size()) ){
            throw new BadRequestException("invalid size of teams");
        }
//       firstofll create the records in the database and just records empty

        int numMatches = teams.size() - 1; // Total matches needed for a single-elimination bracket
        List<Match> matches = new ArrayList<>();

        // Create empty match slots (finals → semifinals → quarterfinals, etc.)
        for (int i = 0; i < numMatches; i++) {
            Match match = new Match();
            match.setTemporada(tournament.get());
            match.setFase(determinePhase(i));
            matches.add(partidoRepository.save(match));
        }
        HashMap<Integer, List<Match>> roundToMatches = new HashMap<Integer, List<Match>>();
        List<Match> matchesByRoundCurr = new ArrayList<>();
        List<Match> matchesByRoundPrev = new ArrayList<>();

        for (int i = 1; i <= determinePhase(numMatches - 1); i++) {
            int finalI = i;
            matchesByRoundCurr = matches.stream()
                    .filter(match -> match.getFase() == finalI)  // Compare phase as an int
                    .collect(Collectors.toList());
            roundToMatches.put(i, matchesByRoundCurr);

            if (i == 1){
                matchesByRoundPrev = matchesByRoundCurr;
                continue;
            }

            int indexPrev = 0;
            for(int indexCurr = 0;indexCurr < matchesByRoundCurr.size();indexCurr+=2){
                Match match1 = matchesByRoundCurr.get(indexCurr);
                Match match2 = matchesByRoundCurr.get(indexCurr+1);
                match1.setNextMatch(matchesByRoundPrev.get(indexPrev));
                match2.setNextMatch(matchesByRoundPrev.get(indexPrev));
                indexPrev++;
            }

            roundToMatches.put(i,matchesByRoundCurr);
            matchesByRoundPrev = matchesByRoundCurr;
        }
//       now matchesByRoundCurr has the last level we can save the teamIds;
        int indexTeams = 0;
        for(int indexCurr = 0;indexCurr < matchesByRoundCurr.size();indexCurr+=2){
            Match match1 = matchesByRoundCurr.get(indexCurr);
            Match match2 = matchesByRoundCurr.get(indexCurr+1);
            match1.setEquipo1(teams.get(indexTeams));
            match1.setEquipo2(teams.get(indexTeams + 1));
            match2.setEquipo1(teams.get(indexTeams + 2));
            match2.setEquipo2(teams.get(indexTeams + 3));
            indexTeams += 4;
        }
        matches = roundToMatches.values()
                .stream()
                .flatMap(List::stream) // Flatten lists into a single stream
                .collect(Collectors.toList());
        for (int i = 0; i < matches.size(); i++) {
            if(matches.get(i).getNextMatch() == null) continue;
        }
        partidoRepository.saveAll(matches);


        return List.of();
    }

    private boolean canGenerateBracket(int teamSize) {
        return teamSize == 2 || teamSize == 4 || teamSize == 8 || teamSize == 16 || teamSize == 32 || teamSize == 64;
    }

    private int determinePhase(int matchIndex) {
        if(matchIndex == 0 ){
            return 1;
        }
        if(matchIndex <= 2){
            return 2;
        }
        if(matchIndex <= 6){
            return 3;
        }
        if(matchIndex <= 14){
            return 4;
        }
        if(matchIndex <= 30){
            return 5;
        }
        if(matchIndex <= 62){
            return 6;
        }
        return 0;
    }


}