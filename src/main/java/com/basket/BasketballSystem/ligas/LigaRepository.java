package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {
    Optional<Liga> findByNombre(String nombre);

    List<Liga> findByNombreContaining(String nombre);



}
