package com.basket.BasketballSystem.equipos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {
    EquipoRepository equipoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public Equipo obtenerEquipoAdminEquipo(String idAdminEquipo){
        //System.out.println("idAdminEquipo: " + idAdminEquipo);
        return equipoRepository.findByidAdminEquipo(idAdminEquipo).orElse(null);

    }
}
