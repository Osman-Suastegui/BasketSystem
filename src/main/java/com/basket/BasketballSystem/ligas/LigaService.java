package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

        LigaRepository ligaRepository;
        @Autowired
        UsuarioRepository usuarioRepository;

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

    public ResponseEntity<String> asignarAdministrador(Long ligaId, String usuarioId) {
        Optional<Liga> ligaOptional = ligaRepository.findById(ligaId);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (!ligaOptional.isPresent() ) {
            throw new BadRequestException("La liga " + ligaId + " no existe");
        }
        if (!usuarioOptional.isPresent()) {
            throw new BadRequestException("El usuario " + usuarioId + " no existe");
        }

        Liga liga = ligaOptional.get();
        Usuario usuario = usuarioOptional.get();
        liga.getAdministradores().add(usuario);
        ligaRepository.save(liga);
        return ResponseEntity.ok("Usuario asignado a la liga exitosamente.");
    }




}
