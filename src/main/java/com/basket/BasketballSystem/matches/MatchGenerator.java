package com.basket.BasketballSystem.matches;

import java.util.List;

public interface MatchGenerator {
    public List<Match> generateBracket(Long tournamentId);
}
