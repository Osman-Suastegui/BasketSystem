package com.basket.BasketballSystem.matches;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.teams_tournaments.TeamTournamentRepository;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.tournaments.TournamentRepository;

import java.util.*;
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
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new BadRequestException("Tournament not found"));

        if (partidoRepository.existsByTournamentId(tournamentId)) {
            throw new BadRequestException("Matches already exist in the tournament");
        }

        List<Team> teams = teamTournamentRepository.findTeamsByTournamentId(tournamentId);
        if(!canGenerateBracket(teams.size()) ){
            throw new BadRequestException("invalid size of teams");
        }
        // Create empty match slots (finals → semifinals → quarterfinals, etc.)
        List<Match> matches = createEmptyMatches(teams.size(), tournament);

        // once the matches have ids when can link one match to antoher by id;
        Map<Integer, List<Match>> roundToMatches = linkMatchRounds(matches);

        int lastPhase = determinePhase(teams.size() - 2);

        // the matches are linked, but we need to fill up the matches with teams in the last level
        if(isPowerOfTwo(teams.size())){
            List<Match> matchesLastRound = roundToMatches.get(lastPhase);
            assignTeamsToRound(matchesLastRound,teams);

            matches = flattenRounds(roundToMatches);
            return partidoRepository.saveAll(matches);
        }
        // when it's not power of two the matches could be in the last round or penultimate round

        List<Match> matchesPenultimateRound = roundToMatches.get(lastPhase - 1);
        matchesPenultimateRound.forEach(match ->
                System.out.println(match.getClavePartido())
        );
        List<Match> matchesLastRound = roundToMatches.get(lastPhase);
        assignTeamsToRound(matchesPenultimateRound,teams);
//        // Create a Set of all teams that participated in a match for quick lookup

        Set<Team> teamsInMatches = matchesPenultimateRound.stream()
                .flatMap(match -> Arrays.stream(new Team[]{match.getEquipo1(), match.getEquipo2()}))
                .collect(Collectors.toSet());

        // Filter out teams that are already in matches
        List<Team> teamsNotAssignedToMatch = teams.stream()
                .filter(team -> !teamsInMatches.contains(team))
                .toList();

//        // Printing the result
        teamsNotAssignedToMatch.forEach(team -> System.out.println(team.getName()));
        assignTeamsToRoundDifferentNumbers(matchesLastRound,teamsNotAssignedToMatch);
        matches = flattenRounds(roundToMatches);
        return partidoRepository.saveAll(matches);
    }

    private List<Match> createEmptyMatches(int teamCount, Tournament tournament) {
        int numMatches = teamCount - 1;
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < numMatches; i++) {
            Match match = new Match();
            match.setTemporada(tournament);
            match.setFase(determinePhase(i));
            matches.add(match);
        }
        return partidoRepository.saveAll(matches);
    }

    private Map<Integer, List<Match>> linkMatchRounds(List<Match> matches) {
        Map<Integer, List<Match>> roundToMatches = new HashMap<>();
        List<Match> previousRoundMatches = new ArrayList<>();

        // Assuming determinePhase returns the round number for a match index
        int maxPhase = determinePhase(matches.size() - 1);
        for (int phase = 1; phase <= maxPhase; phase++) {
            int finalPhase = phase;
            List<Match> currentRoundMatches = matches.stream()
                    .filter(match -> match.getFase() == finalPhase)
                    .collect(Collectors.toList());
            roundToMatches.put(phase, currentRoundMatches);

            if (phase > 1 && !previousRoundMatches.isEmpty()) {
                for (int i = 0, j = 0; i < currentRoundMatches.size() && j < previousRoundMatches.size(); i += 2, j++) {
                    currentRoundMatches.get(i).setNextMatch(previousRoundMatches.get(j));
                    if(i + 1 < currentRoundMatches.size() ){
                        currentRoundMatches.get(i + 1).setNextMatch(previousRoundMatches.get(j));
                    }
                }
            }
            previousRoundMatches = currentRoundMatches;
        }

        return roundToMatches;
    }

    private void assignTeamsToRound(List<Match> matches, List<Team> teams) {
        // Assume firstRoundMatches size is even and teams list is in proper order
        int teamIndex = 0;
        for (int i = 0; i < matches.size(); i ++) {
            Match match1 = matches.get(i);
            match1.setEquipo1(teams.get(teamIndex));
            if(teamIndex + 1 < teams.size()){
                match1.setEquipo2(teams.get(teamIndex + 1));
            }else{
                break;
            }
            teamIndex += 2;
        }
    }

    private void assignTeamsToRoundDifferentNumbers(List<Match> lastRoundMatches, List<Team> teams){

        int indexTeams = 0;
        for(int i = 0 ; i < lastRoundMatches.size();  i++){
            Match match = lastRoundMatches.get(i);
            Match nextMatch = match.getNextMatch();
            if(nextMatch.getEquipo1() != null){
                Team team = nextMatch.getEquipo1();
                match.setEquipo1(teams.get(indexTeams));
                match.setEquipo2(team);
                nextMatch.setEquipo1(null);
            }else {
                Team team = nextMatch.getEquipo2();
                match.setEquipo1(teams.get(indexTeams));
                match.setEquipo2(team);
                nextMatch.setEquipo2(null);
            }
            indexTeams++;
        }

    }

    private boolean canGenerateBracket(int teamSize) {
        return true;
//        return teamSize == 2 || teamSize == 4 || teamSize == 5  || teamSize == 8 || teamSize == 16 || teamSize == 32 || teamSize == 64;
    }

    private List<Match> flattenRounds(Map<Integer, List<Match>> rounds) {
        return rounds.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private int determinePhase(int matchIndex) {
        if (matchIndex == 0) return 1;
        if (matchIndex <= 2) return 2;
        if (matchIndex <= 6) return 3;
        if (matchIndex <= 14) return 4;
        if (matchIndex <= 30) return 5;
        if (matchIndex <= 62) return 6;
        return 0;
    }

    private boolean isPowerOfTwo(int n){
        return (n > 0) && ((n & (n - 1)) == 0);

    }


}