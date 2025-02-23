package com.basket.BasketballSystem.tournaments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {


    List<Tournament> findByNameContaining(String name);

    //recibe el id de la temporada y cambia su estado dependiendo de que mandaron
    @Modifying
    @Transactional
    @Query(value = "UPDATE temporadas SET estado = :estado WHERE id = :temporadaId", nativeQuery = true)
    void updateTemporadaEstado(@Param("temporadaId") Long temporadaId, @Param("estado") String estado);

    @Query("""
           SELECT DISTINCT t 
           FROM Tournament t
           LEFT JOIN FETCH t.userTournaments ut
           LEFT JOIN FETCH ut.user u
           LEFT JOIN FETCH t.teamTournaments tt
           LEFT JOIN FETCH tt.team
           WHERE t.id = :tournamentId
           """)
    Tournament findTournamentWithUsersAndTeams(@Param("tournamentId") Long tournamentId);
}
