package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

        LigaRepository ligaRepository;

        @Autowired
        public LigaService(LigaRepository ligaRepository) {
            this.ligaRepository = ligaRepository;
        }

    public List<Temporada> obtenerTemporadas(Long idLiga) {
        if (idLiga == null) throw new BadRequestException("El id de la liga no puede ser nulo");
        Optional<Liga> liga = ligaRepository.findById(idLiga);
        if(!liga.isPresent()) throw new BadRequestException("La liga " + idLiga + " no existe");

        return liga.get().getTemporadas();

    }

    public List<Usuario> obtenerAdministradores(Long idLiga) {
        if (idLiga == null) throw new BadRequestException("El id de la liga no puede ser nulo");
        Optional<Liga> liga = ligaRepository.findById(idLiga);
        if(!liga.isPresent()) throw new BadRequestException("La liga " + idLiga + " no existe");

        return liga.get().getAdministradores();
    }
}
