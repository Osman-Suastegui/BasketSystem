package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EquipoRepository extends JpaRepository<Equipo, String> {

    @Query("SELECT e FROM Equipo e WHERE e.admin_equipo.usuario = :idAdminEquipo")
    Optional<Equipo> findByidAdminEquipo(@Param("idAdminEquipo") String idAdminEquipo);

    List<Equipo> findByNombreContaining(String nombre);

    Equipo findByNombre(String nombre);




}
