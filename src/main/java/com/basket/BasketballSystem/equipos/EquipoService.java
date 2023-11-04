package com.basket.BasketballSystem.equipos;


import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;

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
        if(equipoRepository.findByidAdminEquipo(idAdminEquipo).orElse(null) == null){
            throw new BadRequestException("No tiene un equipo asignado");
        }
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


    public ResponseEntity<Map<String, Object>> crearEquipo(Equipo equipo) {

        if (equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del equipo no puede ser nulo o vacío");
        }

        String adminEquipo = equipo.getUsuario_Admin_equipo();
        if (adminEquipo == null) {
            throw new BadRequestException("El usuario administrador del equipo no puede ser nulo");
        }

        if (equipoRepository.findById(equipo.getNombre()).isPresent()) {
            throw new BadRequestException("El equipo ya existe");
        }

        if(equipo.getRama() == null){
            throw new BadRequestException("La rama no puede ser nula");
        }

        if(equipo.getCategoria() == null){
            throw new BadRequestException("La categoria no puede ser nula");
        }

        equipoRepository.save(equipo);
        Map<String, Object> team = new HashMap<>();

        team.put("message", "Equipo Creado Exitosamente.");

        return ResponseEntity.ok(team);
    }


    public ResponseEntity<Map<String, Object>> agregarJugador(JugadoresEquipo jugadoresEquipo) {
        if (jugadoresEquipo == null) {
            throw new BadRequestException("El jugador no puede ser nulo");
        }

        if (jugadoresEquipo.getNombreEquipo() == null || jugadoresEquipo.getNombreEquipo().isEmpty()) {
            throw new BadRequestException("El nombre del equipo no puede ser nulo o vacío");
        }

        if (jugadoresEquipo.getJugador().getUsuario() == null) {
            throw new BadRequestException("El usuario del jugador no puede ser nulo");
        }

        if (jugadoresEquipo.getPosicion() == null || jugadoresEquipo.getPosicion().isEmpty()) {
            throw new BadRequestException("La posición del jugador no puede ser nula o vacía");
        }



        Optional<Equipo> equipo = equipoRepository.findById(jugadoresEquipo.getNombreEquipo());
        if(!equipo.isPresent()){
            throw new BadRequestException("El equipo no existe");
        }


        for (JugadoresEquipo jugador : equipo.get().getJugadores()) {
            if(jugador.getJugador().getUsuario().equals(jugadoresEquipo.getJugador().getUsuario())){
                throw new BadRequestException("El jugador ya existe en el equipo");
            }
        }


        equipo.get().addJugador(jugadoresEquipo);
        equipoRepository.save(equipo.get());

        Map<String, Object> team = new HashMap<>();

        team.put("message", "Jugador asignado correctamente.");

        return ResponseEntity.ok(team);
    }
    @Transactional
    public ResponseEntity<Map<String, Object>> eliminarJugador(String nombreEquipo, String nombreJugador) {
        jugadoresEquipoRepository.deleteByJugadorAndEquipo(nombreEquipo, nombreJugador);
        Map<String, Object> team = new HashMap<>();

        team.put("message", "Jugador eliminado exitosamente.");
        return ResponseEntity.ok(team);
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


    public List<Usuario> obtenerJugadoresParaEquipo(String nombreEquipo) {
        List<Usuario> jugadores = jugadoresEquipoRepository.findJugadoresNotInEquipo(nombreEquipo);
            return jugadores;
    }




}
