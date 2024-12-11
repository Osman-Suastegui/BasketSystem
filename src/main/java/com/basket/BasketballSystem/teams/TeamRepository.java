package com.basket.BasketballSystem.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    @Query("SELECT e FROM Team e WHERE e.admin_equipo.usuario = :idAdminEquipo")
    Optional<Team> findByidAdminEquipo(@Param("idAdminEquipo") String idAdminEquipo);

    List<Team> findByNameContaining(String nombre);

    Team findByName(String nombre);

}
