package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.tournaments.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EquipoTemporadaService {

    @Autowired
    TournamentRepository TemporadaRepository;


    @Autowired
    EquipoTemporadaRepository equipoTemporadaRepository;

    @Autowired
    public EquipoTemporadaService(EquipoTemporadaRepository equipoTemporadaRepository) {
        this.equipoTemporadaRepository = equipoTemporadaRepository;
    }


    public ResponseEntity<Map<String, Object>> crearEquipoTemporada(EquipoTemporada equipoTemporada) {

        if(equipoTemporada.getTemporada() == null ){
            throw new BadRequestException("La temporada no puede ser nula o vacia");
        }
        if(equipoTemporada.getEquipo().getNombre() == null ){
            throw new BadRequestException("El equipo no puede ser nulo o vacio");
        }

        Map<String, Object> equipoTemp = new HashMap<>();
        equipoTemporadaRepository.save(equipoTemporada);
        equipoTemp.put("message", "Arbitro agregado exitosamente.");


        return ResponseEntity.ok(equipoTemp);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> eliminarEquipoTemporada(EquipoTemporada equipoTemporada) {

        Long claveTemporada = equipoTemporada.getTemporada().getClaveTemporada();
        String nombreEquipo = equipoTemporada.getEquipo().getNombre();


        equipoTemporadaRepository.deleteByTournamentIdAndTeamNombre(claveTemporada, nombreEquipo);

            equipoTemporadaRepository.delete(equipoTemporada);
        Map<String, Object> EquipoMap = new HashMap<>();
        EquipoMap.put("message", "Equipo eliminado exitosamente.");
            return ResponseEntity.ok(EquipoMap);
    }



    public ResponseEntity<Map<String, Object>> obtenerEquiposTemporada(Long temporadaId) {

        if (temporadaId == null) {
            throw new BadRequestException("La temporada no existe");
        }

        List<Team> teams = equipoTemporadaRepository.findTeamsByClaveTemporada(temporadaId);



        Map<String, Object> nombreEquipoMap = new HashMap<>();
        for (Team team : teams) {
            nombreEquipoMap.put(team.getNombre(), team.getNombre()); // Utiliza el nombre del equipo como clave
        }

        return ResponseEntity.ok(nombreEquipoMap);
    }



    public List<String> obtenerEquiposNoEnTemporada(Long temporadaId) {
        Optional<Tournament> optionalTemporada = TemporadaRepository.findById(temporadaId);
        if(optionalTemporada.isEmpty()) return new ArrayList<>();
        Tournament temp = optionalTemporada.get();

        List<String> equipos = equipoTemporadaRepository.findTeamsNotInTemporadaAndCategoryAndGender(temporadaId);


        return equipos;
    }


}
