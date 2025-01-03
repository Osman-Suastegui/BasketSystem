package com.basket.BasketballSystem.user_tournament;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTournamentRepository extends JpaRepository<UserTournament,Long> {
}
