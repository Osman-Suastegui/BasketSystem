package com.basket.BasketballSystem.user_tournament;

import com.basket.BasketballSystem.tournaments.Tournament;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserTournamentRepository extends JpaRepository<UserTournament,Long> {
    @Query("SELECT DISTINCT ut.tournament FROM UserTournament ut " +
            "WHERE ut.user.id = :userId")
    Page<Tournament> findDistinctTournamentsByUserId(@Param("userId") Long userId, Pageable pageable);
}
