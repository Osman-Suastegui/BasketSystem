package com.basket.BasketballSystem.matches;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.teams.TeamRepository;
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

        // the matches are linked but we need to fill up the matches with teams in the last level
        int lastPhase = determinePhase(teams.size());
        List<Match> matchesLastRound = roundToMatches.get(lastPhase);
        assignTeamsToLastRound(matchesLastRound,teams);

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
                    currentRoundMatches.get(i + 1).setNextMatch(previousRoundMatches.get(j));
                }
            }
            previousRoundMatches = currentRoundMatches;
        }

        return roundToMatches;
    }

    private void assignTeamsToLastRound(List<Match> matches, List<Team> teams) {
        // Assume firstRoundMatches size is even and teams list is in proper order
        int teamIndex = 0;
        for (int i = 0; i < matches.size(); i += 2) {
            Match match1 = matches.get(i);
            Match match2 = matches.get(i + 1);
            match1.setEquipo1(teams.get(teamIndex));
            match1.setEquipo2(teams.get(teamIndex + 1));
            match2.setEquipo1(teams.get(teamIndex + 2));
            match2.setEquipo2(teams.get(teamIndex + 3));
            teamIndex += 4;
        }
    }

    private boolean canGenerateBracket(int teamSize) {
        return teamSize == 2 || teamSize == 4 || teamSize == 8 || teamSize == 16 || teamSize == 32 || teamSize == 64;
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


}