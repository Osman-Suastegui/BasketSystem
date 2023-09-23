package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {


    List<Temporada> findByNombreTemporadaContaining(String nombre);
}
