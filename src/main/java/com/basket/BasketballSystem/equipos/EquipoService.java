package com.basket.BasketballSystem.equipos;


import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {
    @Autowired
    EquipoRepository equipoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public Equipo obtenerEquipoAdminEquipo(String idAdminEquipo){
        //System.out.println("idAdminEquipo: " + idAdminEquipo);
        return equipoRepository.findByidAdminEquipo(idAdminEquipo).orElse(null);

    }

    public List<JugadoresEquipo> obtenerJugadoresPorNombreDelEquipo(String nombreEquipo) {
        if(nombreEquipo == null){
          throw new BadRequestException("El nombre del equipo no puede ser nulo");
        }
        Optional<Equipo> equipo = equipoRepository.findById(nombreEquipo);
        if(equipo.isPresent()){
            return equipo.get().getJugadores();
        }

        return null;
    }


    public ResponseEntity<String> crearEquipo(Equipo equipo) {
        if (equipo == null) {
            return ResponseEntity.badRequest().body("El objeto Equipo no puede ser nulo");
        }

        if (equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre del equipo no puede ser nulo o vacío");
        }

        String adminEquipo = equipo.getUsuario_Admin_equipo();
        if (adminEquipo == null) {
            return ResponseEntity.badRequest().body("El usuario administrador del equipo no puede ser nulo");
        }

        if (equipoRepository.findById(equipo.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().body("El equipo ya existe");
        }

        equipoRepository.save(equipo);
        return ResponseEntity.ok("Equipo creado correctamente");
    }




}
