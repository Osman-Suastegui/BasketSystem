package com.basket.BasketballSystem.equipos;


import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.DTO.JugadoresEquipoDTO;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;

import com.basket.BasketballSystem.temporadas.Categoria;
import com.basket.BasketballSystem.temporadas.Rama;
import com.basket.BasketballSystem.usuarios.Genero;
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


    public ResponseEntity<Map<String, Object>> agregarJugador(JugadoresEquipoDTO jugadoresEquipoDTO) {
        // Crear una instancia de JugadoresEquipo
        JugadoresEquipo jugadoresEquipo = new JugadoresEquipo();
        //valida que la posicion no sea nula
        if(jugadoresEquipoDTO.getPosicion() == null){
            throw new BadRequestException("La posicion no puede ser nula");
        }
        jugadoresEquipo.setPosicion(jugadoresEquipoDTO.getPosicion());

        // Buscar y configurar el equipo
        String equipoNombre = jugadoresEquipoDTO.getEquipoNombre();
        Equipo equipo = equipoRepository.findByNombre(equipoNombre);

        if (equipo == null) {
            throw new BadRequestException("El equipo no existe");
        }

        // Buscar y configurar el jugador
        String jugadorUsuario = jugadoresEquipoDTO.getJugadorUsuario();

        Usuario jugador = usuarioRepository.findByUsuario(jugadorUsuario).orElse(null);
        if (jugador == null) {
            throw new BadRequestException("El usuario del jugador no existe");
        }

        if (jugador == null) {
            throw new BadRequestException("El usuario del jugador no existe");
        }

        jugadoresEquipo.setEquipo(equipo);
        jugadoresEquipo.setJugador(jugador);

        // Verificar si el jugador ya existe en el equipo
        for (JugadoresEquipo jugadorEnEquipo : equipo.getJugadores()) {
            if (jugadorEnEquipo.getJugador().getUsuario().equals(jugadorUsuario)) {
                throw new BadRequestException("El jugador ya existe en el equipo");
            }
        }

        // Guardar la instancia de JugadoresEquipo en el repositorio
        equipo.addJugador(jugadoresEquipo);
        equipoRepository.save(equipo);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Jugador asignado correctamente");

        return ResponseEntity.ok(response);
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
        Equipo equipo = equipoRepository.findByNombre(nombreEquipo);

         Rama rama = equipo.getRama();
         String ramaString = rama.toString();
        Genero genero = ramaString.equals("MASCULINO") ? Genero.MASCULINO : Genero.FEMENINO;
         Categoria categoria = equipo.getCategoria();
        int edadInicio = 0;
        int edadFin = 0;
        if(categoria == Categoria.PREBENJAMIN) {
            edadInicio = 6;
            edadFin = 7;
        }else if (categoria == Categoria.BENJAMIN) {
            edadInicio = 8;
            edadFin = 9;
        }else if (categoria == Categoria.ALEVIN) {
            edadInicio = 10;
            edadFin = 11;
        }else if (categoria == Categoria.INFANTIL) {
            edadInicio = 12;
            edadFin = 13;
        }else if (categoria == Categoria.CADETE) {
            edadInicio = 14;
            edadFin = 15;
        }else if (categoria == Categoria.JUNIOR) {
            edadInicio = 16;
            edadFin = 17;
        }else if (categoria == Categoria.SUB22) {
            edadInicio = 18;
            edadFin = 21;
        }else if (categoria == Categoria.SENIOR) {
            edadInicio = 22;
            edadFin = 200;
        }else{
            edadInicio = 0;
            edadFin = 200;
        }

        List<Usuario> jugadores = jugadoresEquipoRepository.findJugadoresNotInEquipoWithAgeAndGenderCondition(edadInicio, edadFin,genero, nombreEquipo);
            return jugadores;
    }







}
