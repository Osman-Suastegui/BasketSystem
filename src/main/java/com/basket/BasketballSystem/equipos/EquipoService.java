package com.basket.BasketballSystem.equipos;


import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoService;
import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EquipoService {
    @Autowired
    EquipoRepository equipoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;


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


    public ResponseEntity<String> agregarJugador(JugadoresEquipo jugadoresEquipo) {
        if (jugadoresEquipo == null) {
            return ResponseEntity.badRequest().body("El objeto JugadoresEquipo no puede ser nulo");
        }

        if (jugadoresEquipo.getNombreEquipo() == null || jugadoresEquipo.getNombreEquipo().isEmpty()) {
            return ResponseEntity.badRequest().body("El equipo no puede ser nulo");
        }

        if (jugadoresEquipo.getJugador().getUsuario() == null) {
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");
        }

        if (jugadoresEquipo.getPosicion() == null || jugadoresEquipo.getPosicion().isEmpty()) {
            return ResponseEntity.badRequest().body("La posición no puede ser nula o vacía");
        }



        Optional<Equipo> equipo = equipoRepository.findById(jugadoresEquipo.getNombreEquipo());
        if(!equipo.isPresent()){
            return ResponseEntity.badRequest().body("El equipo no existe");
        }


        for (JugadoresEquipo jugador : equipo.get().getJugadores()) {
            if(jugador.getJugador().getUsuario().equals(jugadoresEquipo.getJugador().getUsuario())){
                return ResponseEntity.badRequest().body("El usuario ya se encuentra en el equipo");
            }
        }


        equipo.get().addJugador(jugadoresEquipo);
        equipoRepository.save(equipo.get());


        return ResponseEntity.ok("Jugador asignado correctamente");
    }
    @Transactional
    public ResponseEntity<String> eliminarJugador(String nombreEquipo, String nombreJugador) {
        jugadoresEquipoRepository.deleteByJugadorAndEquipo(nombreEquipo, nombreJugador);
        return ResponseEntity.ok("Jugador eliminado exitosamente.");
    }

    public List<Map<String,Object>> buscarEquipoPorNombre(String nombre) {

        List<Equipo> equipos = equipoRepository.findByNombreContaining(nombre);

        List<Map<String,Object>> equiposMap = new ArrayList<>();


        for(Equipo equipo: equipos){
            Map<String,Object> e = new HashMap<>();
            e.put("nombre",equipo.getNombre());
            equiposMap.add(e);
        }


        return equiposMap;


    }
}
